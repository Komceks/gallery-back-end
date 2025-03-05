# Greetings Module (MR1)

Spring Boot "Hello, World!" app

## API documentation

### GET /api/greeting

- **Description:**
  Returns a greeting message
- **Parameters:**

  - **name** (query parameter, optional): the name of the person to greet.
- **Response:**

```
200: 

{
	"id": 1,
	"content": "Hello, Bob!",
	"timestamp": "1739803179852"
}
```

# Image Upload Module (MR2)

Adds functionality to upload images along with metadata to a PostgreSQL DB.

## API documentation

### POST /api/gallery/upload

- **Description:**
Upload image along with metadata
- **Request body:**

```json
{
    "imageName": "Simple image",
    "description": "Simple image description",
    "authorName": "Joe Foe",
    "date": "2025-02-18",
    "imageFile": "<binary_data>",
    "tags": ["tag1", "tag2"],
    "uploadDate": "YYYY-MM-DD HH:mm:ss"
}
```
- **Response:**

```
200: 

"Image uploaded successfully"

400:
{
	"timestamp": "2025-02-18T13:13:08.093+00:00",
	"status": 400,
	"error": "Bad Request",
	"path": "/gallery/upload"
}
```

- **Database Storage:**
  The backend processes the request and stores the image as a BLOB in a PostgreSQL DB.

## Future enhancements

- Add more robust validation.
- Incorporate image compression or resizing.
- Add proper response


# Image Upload Module (MR3)

Masonry layout gallery page with implemented simple searching functionality using JPA Criteria Queries.

## API documentation

### GET /api/gallery

- **Description:**
  Get images in batches
- **Request parameters:**

```json
{
    "startIdx": 0,
    "endIdx": 25
}
```
- **Response:**

```
200: 

[
	{
		"id": Long,
		"imageName": String,
		"description": String,
		"authorName": String,
		"date": Date,
		"tags": [
			String
		],
		"uploadDate": Timestamp,
		"thumbnail": Blob
	}
]

400:
{
	"timestamp": timestamp,
	"status": 400,
	"error": "Bad Request",
	"path": "/gallery"
}
```

### GET /api/gallery/search

- **Description:**
  Get searched images in batches
- **Request parameters:**

```json
{
    "startIdx": 0,
    "endIdx": 25,
    "search": "String"
}
```
- **Response:**

```
200: 

[
	{
		"id": Long,
		"imageName": String,
		"description": String,
		"authorName": String,
		"date": Date,
		"tags": [
			String
		],
		"uploadDate": Timestamp,
		"thumbnail": Blob
	}
]

400:
{
	"timestamp": timestamp,
	"status": 400,
	"error": "Bad Request",
	"path": "/gallery/search"
}
```