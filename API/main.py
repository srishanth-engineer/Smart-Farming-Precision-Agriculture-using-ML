from fastapi import FastAPI, File, UploadFile
from pydantic import BaseModel
import numpy as np
import tensorflow as tf
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing import image
from sklearn.preprocessing import LabelEncoder
import io
import torch
import torchvision.transforms as transforms
from torchvision import models
import joblib  # Import for loading the crop recommendation model
from PIL import Image


app = FastAPI()

# Load the trained models
modelWeed = load_model("weed_detection_model.h5")
modelPest = load_model("pestIdentification.h5")
#modelCrop = joblib.load("CropRecommendetion_RF_Model.pkl")  # Using joblib to load the .pkl file
modelCrop = joblib.load("random_forest_model1.pkl")
modelCropNuts = joblib.load("random_forest_model_soil_Nutrients.pkl")
modelFerti = joblib.load("bagging_model_Fertilizer.pkl")

# Device Configuration
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
print(f"Using device: {device}")

# Define transformations (same as training)
transform = transforms.Compose([
    transforms.Resize((224, 224)),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])  # ResNet normalization
])

# Load the trained ResNet model for maize disease detection
num_classes_maize = 7  # Update this based on the number of classes in your dataset
modelMaize = models.resnet18(pretrained=False)  # Use the correct torchvision model
modelMaize.fc = torch.nn.Linear(modelMaize.fc.in_features, num_classes_maize)  # Adjust output layer
modelMaize.load_state_dict(torch.load("maize_classifier1.pth", map_location=device))  # Load trained weights
modelMaize.to(device)
modelMaize.eval()  # Set model to evaluation mode

# Load the trained ResNet model for Tomato disease detection
num_classes_tomato = 5  # Update this based on the number of classes in your dataset
modelTomato = models.resnet18(pretrained=False)  # Use the correct torchvision model
modelTomato.fc = torch.nn.Linear(modelTomato.fc.in_features, num_classes_tomato)  # Adjust output layer
modelTomato.load_state_dict(torch.load("Tomato_model_25.pth", map_location=device))  # Load trained weights
modelTomato.to(device)
modelTomato.eval()  # Set model to evaluation mode


# Define class names (ensure this matches your training dataset)
class_names_maize = [
    "fall armyworm", "grasshopper", "healthy", "leaf beetle",
    "leaf blight", "leaf spot", "streak virus"
]

# Define class names (ensure this matches your training dataset)
class_names_tomato = [
    "verticulium wilt", "healthy", "leaf blight", 
    "leaf curl", "septoria leaf spot"
]

# Function to preprocess image
def preprocess_image_torch(img_bytes):
    img = Image.open(io.BytesIO(img_bytes)).convert("RGB")  # Ensure RGB format
    img = transform(img).unsqueeze(0)  # Apply transformations and add batch dimension
    return img.to(device)
#class DummyModel:
#    def predict(self, X):
#        return [0]  # Always returns 0 for testing (index of "Urea")

#modelFerti = DummyModel()

# Define class names
class_names_weed = [
    "Black-grass", "Charlock", "Cleavers", "Common Chickweed", "Common wheat",
    "Fat Hen", "Loose Silky-bent", "Maize", "Scentless Mayweed",
    "Shepherd’s Purse", "Small-flowered Cranesbill", "Sugar beet"
]

class_names_pest = [
    "aphids", "armyworm", "beetle", "bollworm", "grasshopper",
    "mites", "mosquito", "sawfly", "stem_borer"
]

crop_mapping = {
    'rice': 1, 'maize': 2, 'chickpea': 3, 'kidneybeans': 4, 'pigeonpeas': 5,
    'mothbeans': 6, 'mungbean': 7, 'blackgram': 8, 'lentil': 9, 'pomegranate': 10,
    'banana': 11, 'mango': 12, 'grapes': 13, 'watermelon': 14, 'muskmelon': 15,
    'apple': 16, 'orange': 17, 'papaya': 18, 'coconut': 19, 'cotton': 20,
    'jute': 21, 'coffee': 22
}

crop_Nuts_mapping = {
    'pomegranate': 1, 'mango': 2, 'grapes': 3,
    'mulberry': 4, 'ragi': 5, 'potato': 6
}

crop_classes = [
    'Maize', 'Sugarcane', 'Cotton', 'Tobacco', 'Paddy', 'Barley', 'Wheat',
    'Millets', 'Oil seeds', 'Pulses', 'Ground Nuts'
]

soil_classes = ['Sandy', 'Loamy', 'Black', 'Red', 'Clayey']

fertilizer_classes = ['Urea', 'DAP', '14-35-14', '28-28', '17-17-17', '20-20', '10-26-26']


# Create label encoders for crop and soil types
crop_encoder = LabelEncoder()
soil_encoder = LabelEncoder()

# Fit the encoders with predefined classes
crop_encoder.fit(crop_classes)
soil_encoder.fit(soil_classes)

# Reverse the crop mapping to get names from numerical predictions
crop_labels = {v: k for k, v in crop_mapping.items()}

# Reverse the crop mapping for nutrients to get names from numerical predictions
crop_Nuts_labels = {v: k for k, v in crop_Nuts_mapping.items()}

# Reverse mapping for fertilizer names if needed
fertilizer_labels = {0: "Urea", 1: "DAP", 2: "14-35-14", 3: "28-28-28", 4: "Urea"}  # Adjust based on training data

# Define input schema using Pydantic
class CropInput(BaseModel):
    n: float
    p: float
    k: float
    temp: float
    humidity: float
    ph: float
    rainfall: float
    soilType: int
    
class CropNutsInput(BaseModel):
    n: float
    p: float
    k: float
    ph: float
    ec: float
    s: float
    cu: float
    fe: float
    mn: float
    zn: float  
    b: float
   
class FertilizerInput(BaseModel):
    temperature: float
    humidity: float
    moisture: float
    soil_type: str
    crop_type: str
    nitrogen: float
    potassium: float
    phosphorous: float   


def preprocess_image(img_bytes):
    """Convert image bytes to a preprocessed model input."""
    img = Image.open(io.BytesIO(img_bytes)).convert("RGB")  # Convert to RGB
    img = img.resize((224, 224))  # Resize to match model input size
    img_array = np.array(img) / 255.0  # Normalize
    img_array = np.expand_dims(img_array, axis=0)  # Add batch dimension
    return img_array


@app.post("/predict-weed")
async def predict_weed(file: UploadFile = File(...)):
    try:
        img_bytes = await file.read()
        processed_img = preprocess_image(img_bytes)

        # Get prediction
        prediction = modelWeed.predict(processed_img)
        predicted_class = np.argmax(prediction)
        confidence = float(np.max(prediction))

        return {"predicted_class": class_names_weed[predicted_class], "confidence": confidence}
    except Exception as e:
        return {"error": str(e)}


@app.post("/predict-pest")
async def predict_pest(file: UploadFile = File(...)):  # Changed function name
    try:
        img_bytes = await file.read()
        processed_img = preprocess_image(img_bytes)

        # Get prediction
        prediction = modelPest.predict(processed_img)
        predicted_class = np.argmax(prediction)
        confidence = float(np.max(prediction))

        return {"predicted_class": class_names_pest[predicted_class], "confidence": confidence}
    except Exception as e:
        return {"error": str(e)}

@app.post("/predict-maize")
async def predict_maize(file: UploadFile = File(...)):
    try:
        img_bytes = await file.read()
        processed_img = preprocess_image_torch(img_bytes)

        # Perform inference
        with torch.no_grad():
            prediction = modelMaize(processed_img)

        predicted_class = torch.argmax(prediction, dim=1).item()
        confidence = torch.softmax(prediction, dim=1)[0][predicted_class].item()

        return {"predicted_class": class_names_maize[predicted_class], "confidence": round(confidence, 4)}

    except Exception as e:
        return {"error": str(e)}
    
@app.post("/predict-tomato")
async def predict_tomato(file: UploadFile = File(...)):
    try:
        img_bytes = await file.read()
        processed_img = preprocess_image_torch(img_bytes)

        # Perform inference
        with torch.no_grad():
            prediction = modelTomato(processed_img)

        predicted_class = torch.argmax(prediction, dim=1).item()
        confidence = torch.softmax(prediction, dim=1)[0][predicted_class].item()

        return {"predicted_class": class_names_tomato[predicted_class], "confidence": round(confidence, 4)}

    except Exception as e:
        return {"error": str(e)}

@app.post("/predict")
def predict_best_crops(input_data: CropInput):
    try:
        # Convert input to numpy array
        input_features = np.array([
            input_data.n, input_data.p, input_data.k, 
            input_data.temp, input_data.humidity, 
            input_data.ph, input_data.rainfall, input_data.soilType
        ]).reshape(1, -1)

        # Predict probabilities for all crops
        probabilities = modelCrop.predict_proba(input_features)[0]

        # Get top 5 crop indices with highest probabilities
        top_5_indices = np.argsort(probabilities)[::-1][:5]

        # Filter crops with probability > 0%
        top_5_crops = [{"crop": crop_labels[i+1], "probability": round(probabilities[i], 4)}
                    for i in top_5_indices if probabilities[i] > 0]

        return {"recommended_crops": top_5_crops}
    except Exception as e:
        return {"error": str(e)}
        
        
        
@app.post("/predictCropNuts")
def predict_best_crops(input_data: CropNutsInput):
    try:
        # Convert input to numpy array
        input_features = np.array([
            input_data.n, input_data.p, input_data.k, 
            input_data.ph, input_data.ec, input_data.fe,
            input_data.s, input_data.cu, input_data.mn, input_data.zn, input_data.b,
        ]).reshape(1, -1)

        # Predict probabilities for all crops
        probabilities = modelCropNuts.predict_proba(input_features)[0]

        # Get top 5 crop indices with highest probabilities
        top_5_indices = np.argsort(probabilities)[::-1][:5]

        # Filter crops with probability > 0%
        top_5_crops = [{"crop": crop_Nuts_labels[i+1], "probability": round(probabilities[i], 4)}
                    for i in top_5_indices if probabilities[i] > 0]

        return {"recommended_crops": top_5_crops}
    except Exception as e:
        return {"error": str(e)}


@app.post("/predict-fertilizer")
def predict_fertilizer(input_data: FertilizerInput):
    try:
        # Validate Crop Type
        if input_data.crop_type not in crop_classes:
            return {"error": f"Invalid Crop Type: {input_data.crop_type}. Must be one of {crop_classes}"}

        # Validate Soil Type
        if input_data.soil_type not in soil_classes:
            return {"error": f"Invalid Soil Type: {input_data.soil_type}. Must be one of {soil_classes}"}

        # Encode categorical variables
        encoded_crop = crop_classes.index(input_data.crop_type)
        encoded_soil = soil_classes.index(input_data.soil_type)

        # Prepare input feature array
        input_features = np.array([
            input_data.temperature, input_data.humidity, input_data.moisture,
            input_data.nitrogen, input_data.potassium,
            input_data.phosphorous, encoded_soil, encoded_crop
        ]).reshape(1, -1)

        # Predict fertilizer
        recommended_fertilizer = modelFerti.predict(input_features)[0]


        #predicted_fertilizer = fertilizer_classes[prediction_index]
        return {"recommended_fertilizer": recommended_fertilizer}

    
    except Exception as e:
        return {"error": str(e)}