package com.github.dnvriend.scaliketests

import com.github.dnvriend.{ TestSpec, UserTableRow }

class UserRepositoryTest extends TestSpec {
  it should "user repo should be empty" in {
    userRepository.allUsers.futureValue shouldBe empty
  }

  it should "initialize with users" in {
    userRepository.init.toTry should be a 'success
    userRepository.allUsers.futureValue should not be empty
  }

  it should "find by id" in {
    userRepository.init.toTry should be a 'success
    val users = userRepository.allUsers.futureValue
    users should not be empty
    val userId = userRepository.allUsers.futureValue.head.id.value
    userRepository.findById(userId).futureValue.value shouldBe UserTableRow(Option(5), "Bill", "Gates")
  }
}
