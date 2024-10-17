# Stage 1: Build the Angular application
FROM node:21.1 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the package.json and package-lock.json files to install dependencies
COPY package*.json ./

# Install Angular CLI and application dependencies
RUN npm install -g @angular/cli
RUN npm install

# Copy the rest of the application code to the working directory
COPY . .

# Build the Angular application for production
RUN ng build --prod

# Stage 2: Serve the application using Nginx
FROM nginx:alpine

# Copy the built application from the previous stage
COPY --from=build /app/dist/<your-angular-app-name> /usr/share/nginx/html

# Copy a default nginx configuration file (optional)
COPY nginx.conf /etc/nginx/nginx.conf

# Expose port 80
EXPOSE 80

# Start Nginx when the container launches
CMD ["nginx", "-g", "daemon off;"]
