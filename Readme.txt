add date with review

add role with user (run this every time a user adds review to check how many reviews he has)

everytime time user does a review check his role and multiply the review score based on role

movie:
id
name
genre
year of release

user:
name


review table:
username
movie name
review_score



{
  "genre": [
    {
      "genreName": "comedy"
    },
    {
      "genreName": "thriller"
    }
  ],
  "movieName": "pulp fiction",
  "releaseDate": "2000"
}

{
  "dateOfReview": "2021-01-01",
  "id": {},
  "movie": {
    "id": 1,
    "movieName": "pulp fiction",
    "releaseDate": "2000"
  },
  "score": 10,
  "user": {
    "currentRole": "viewer",
    "id": 1,
    "username": "Rahul"
  }
}


{

  "username": "Rahul"
}


{
  "dateOfReview": "2021-01-01",
  "movie": {
    "id": 1
  },
  "score": 2,
  "user": {
    "id": 1
  }
}