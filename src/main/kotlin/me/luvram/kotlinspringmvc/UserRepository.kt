package me.luvram.kotlinspringmvc

import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, String>{
}
