# Greetings Module (MR1)

Spring Boot "Hello, World!" app

## API documentation

### GET /greeting

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
