
# 📘 MemoCam Ktor API Documentation

**Base URL:** `http://192.168.1.35:8080/`  
**Authentication:** Bearer Token (`Authorization: Bearer <token>`)

---

## 🧾 Authentication

### 🔐 Register

- **POST** `/auth/register`

#### Request Body
```json
{
  "username": "mrtest",
  "email": "test@gamil.com",
  "password": "123456"
}
```

#### Response (Success `200 OK`)
```json
{
  "success": true,
  "message": "User created successfully",
  "data": {
    "token": "eyJhbGc...Ikmg",
    "user": {
      "id": "string",
      "username": "mrtest",
      "email": "test@gamil.com"
    }
  }
}
```

#### Response (Error)
```json
{
  "message": "Username or email already exists"
}
```

---

### 🔑 Login

- **POST** `/auth/login`

#### Request Body
```json
{
  "username": "mrtest",
  "password": "123456"
}
```

#### Response (Success)
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGc...Ikmg",
    "user": {
      "id": "string",
      "username": "mrtest",
      "email": "test@gamil.com"
    }
  }
}
```

#### Response (Failure)
```json
{
  "message": "Invalid credentials"
}
```

---

## 📁 Folders

### 📄 Get All Folders

- **GET** `/folders`

#### Response
```json
{
  "success": true,
  "message": "Folders retrieved successfully",
  "data": [
    {
      "id": "string",
      "name": "Very First Folder",
      "createdAt": 1750499462731
    }
  ]
}
```

---

### ➕ Create Folder

- **POST** `/folders`

#### Request Body
```json
{
  "name": "Another folder"
}
```

#### Response (Success)
```json
{
  "success": true,
  "message": "Folder created successfully",
  "data": {
    "id": "string",
    "name": "Very First Folder",
    "createdAt": 1750499462731
  }
}
```

#### Response (Error)
```json
{
  "message": "Folder name cannot be empty"
}
```

---

### ❌ Delete Folder

- **DELETE** `/folders/{folderId}`

#### Response (Success)
```json
{
  "success": true,
  "message": "Folder deleted successfully"
}
```

#### Response (Failure)
```json
{
  "message": "Folder not found"
}
```

---

### 🖼️ Update Poster Image

- **PUT** `/folders/{folderId}/{imageId}`

#### Response (Success)
```json
{
  "success": true,
  "message": "Poster image updated successfully",
  "data": {
    "id": "string",
    "name": "Personal Face",
    "userId": "string",
    "posterImage": "string",
    "createdAt": 1750505754478
  }
}
```

#### Response (Failure)
```json
{
  "message": "Image not found"
}
```

---

## 🖼️ Images

### 📷 Get Images in Folder

- **GET** `/images/folder/{folderId}`

#### Response
```json
{
  "success": true,
  "message": "Images retrieved successfully",
  "data": {
    "images": [],
    "currentPage": 1,
    "totalPages": 0,
    "totalCount": 0,
    "hasNext": false,
    "hasPrevious": false
  }
}
```

#### Error
```json
{
  "message": "Folder not found"
}
```

---

### 📤 Upload Image

- **POST** `/images/upload/{folderId}`
- **FormData** (Key: `image`, Type: `file`)

#### Response
```json
{
  "success": true,
  "message": "Image uploaded successfully",
  "data": {
    "id": "string",
    "fileName": "example.jpg",
    "originalName": "example.jpg",
    "size": 394839,
    "contentType": "image/jpeg",
    "uploadedAt": 1750500401780
  }
}
```

#### Error
```json
{
  "message": "No file uploaded"
}
```

---

### 🖼️ Get Image by ID

- **GET** `/images/{imageId}`

> This returns the raw image. No structured JSON response is expected.

---

### 🗑️ Delete Image

- **DELETE** `/images/{imageId}`

#### Response (Success)
```json
{
  "success": true,
  "message": "Image deleted successfully"
}
```

#### Error
```json
{
  "message": "Image not found"
}
```

---

## 🛠️ Environment Variables (Used in Postman)

| Variable         | Value                                              |
|------------------|----------------------------------------------------|
| `base_url`       | `http://192.168.1.35:8080/`                         |
| `current_token`  | Your JWT token                                     |
| `current_folder` | e.g. `6856991a9e48cd2c58a6405d`                     |
| `current_image`  | e.g. `685684311f5551359f894592`                     |
