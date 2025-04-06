# Use Node.js for building React
FROM node:18

# Set working directory
WORKDIR /app

# Copy package.json and install dependencies
COPY package.json package-lock.json ./
RUN npm install  

# Copy source code
COPY . .  

# Build the project
RUN npm run build  

# Expose port (if needed)
EXPOSE 5173  

# Start the frontend
CMD ["npm", "start"]
