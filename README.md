# Best Matched Restaurants

An API project to find a best matched restaurants by a defined criteria.

You can search the best restaurant to go by providing the following criteria:
- Name
- Distance (in miles)
- Price
- Rating
- Cuisine type

This API will return the best five matched restaurant based on the criteria you provided. You can use all the criteria or just some of them.

## How to use

### Request all restaurants registered
```
[GET]
[URI]: /v1/restaurants
[HEADER]: {
    "Content-Type": "application/json"
    "Accept": "application/json"
}

[RESPONSE]: {

}

```
### Request one restaurant by id
**[GET]**
[HOST]/v1/restaurants/{id}
