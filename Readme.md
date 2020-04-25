# JSON-LD extractor
To use just run the src\main\scala\server.scala file. It will setup a server in localhost:8080

This program has 2 endpoints : 
- /{container}
    - this returns the container info is it's available
    - The only containers available are NewsArticle and Movie

- /{container}/{resource}
    - after querying a container, you can use any id available of any resource (the last part of the @id url) to obtain the full JSON+LD of the resource
    
    

### Example

1. run server
2. get movie container at: http://localhost:8080/movie

        {
            "@id": "http://localhost:8080/movie",
            "@type": [
                "http://www.w3.org/ns/ldp#BasicContainer"
            ],
            "http://purl.org/dc/terms/title": [
                {
                    "@value": "Container of movie resources"
                }
            ],
            "http://www.w3.org/ns/ldp#contains": [
                
                {
                    "@id": "  http://www.imdb.com/title/tt0012349/"
                }
            ]
        }


finally, we query the movie with id *tt0012349* at: http://localhost:8080/movie/tt0012349 and get the full JSON+LD
