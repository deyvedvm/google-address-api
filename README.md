# Google Address API

Given an address it queries the Google API and returns latitude and longitude.

 Desired features:

- [x] Crud
- [x] Google Service
- [x] Dockerfile
- [x] Unit Tests
- [ ] Integration Tests
- [ ] Pagination
- [ ] Use DTO
- [ ] Swagger
- [ ] Mongo Atlas
- [ ] Travis CI
- [ ] Deploy Heroku
- [ ] Deploy Docker Hub


 Folder structure
 
 ```text
.
├── main
│   ├── java
│   │   └── dev
│   │       └── deyve
│   │           └── googleaddressapi
│   │               ├── config
│   │               │   └── GoogleMapsConfig.java
│   │               ├── controllers
│   │               │   └── AddressController.java
│   │               ├── dtos
│   │               │   └── AddressDTO.java
│   │               ├── errors
│   │               │   ├── AddressNotFoundException.java
│   │               │   └── ControllerAdvisor.java
│   │               ├── GoogleAddressApiApplication.java
│   │               ├── models
│   │               │   └── Address.java
│   │               ├── repositories
│   │               │   └── AddressRepository.java
│   │               └── services
│   │                   ├── AddressService.java
│   │                   └── GoogleRestService.java
│   └── resources
│       ├── application.yaml
│       ├── static
│       └── templates
└── test
    ├── java
    │   └── dev
    │       └── deyve
    │           └── googleaddressapi
    │               ├── config
    │               │   └── MongoDBConfig.java
    │               ├── controllers
    │               │   ├── AddressControllerIT.java
    │               │   └── AddressControllerTest.java
    │               └── GoogleAddressApiApplicationTests.java
    └── resources

23 directories, 15 files

```

Author

[Deyve Vieira](https://www.deyve.dev/)


