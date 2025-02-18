# Image Upload Module (MR2)

Adds functionality to upload images along with metadata to a PostgreSQL DB.

## API documentation

### POST /images/upload

- **Description:**
Upload image along with metadata
- **Request body:**

```json
{
    "name": "Simple image",
    "description": "Simple image description",
    "author": "Joe Foe",
    "date": "2025-02-18",
    "tags": ["tag1", "tag2"],
    "file": <binary_data>
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
	"path": "/images/upload"
}
```

- **Database Storage:**
  The backend processes the request and stores the image as a BLOB in a PostgreSQL DB.

## Future enhancements

- Add more robust validation.
- Incorporate image compression or resizing.
- Add proper response
