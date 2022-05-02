package com.springboot.tutorial.demobank.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.springboot.tutorial.demobank.model.Bank
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {


    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAllBanks {
        @Test
        fun `should return all banks` () {
            //given

            //when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") {value("abc")}
                    jsonPath("$[0].trust") {value(1.0)}
                    jsonPath("$[0].transactionFee") {value(1)}
                }
        }
    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetABank {
        @Test
        fun `should return banks with given accountNumber` () {
            //given
            val accountNumber = "abc"

            //when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") {value("abc")}
                    jsonPath("$.trust") {value(1.0)}
                    jsonPath("$.transactionFee") {value(1)}
                }
        }

        @Test
        fun `should return not found if account number does not exist` () {
            //given
            val accountNumber = "does_not_exist"

            //when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class CreateBank {

        @Test
        fun `create a new bank` () {
            //given
            val newBank = Bank("acct123", 2.0, 50)
            //when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { MediaType.APPLICATION_JSON }
                    jsonPath("$.accountNumber") {value(newBank.accountNumber)}
                    jsonPath("$.trust") {value(newBank.trust)}
                    jsonPath("$.transactionFee") {value(newBank.transactionFee)}
                }
        }

        @Test
        fun `should return BAD REQUEST if bank with existing account number already exist` () {
            //given
            val existingBank = Bank("abc", 1.0, 1)

            //when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(existingBank)
            }

            //then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }

    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateBankParameters {

        @Test
        fun `should update an existing bank`() {
            //given
            val updatedBank = Bank("abc", 2.0, 60)
            //when
            val performPost = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }
            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { MediaType.APPLICATION_JSON }
                    jsonPath("$.accountNumber") { value(updatedBank.accountNumber) }
                    jsonPath("$.trust") { value(updatedBank.trust) }
                    jsonPath("$.transactionFee") { value(updatedBank.transactionFee) }
                }
        }

        @Test
        fun `should return bad request if bank doesnot exist`() {
            //given
            val updatedBank = Bank("blah", 2.0, 60)
            //when
            val performPost = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }
            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }


    @Nested
    @DisplayName("DELETE /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class deleteBank {

        @Test
        fun `should delete an existing bank`() {
            //given
            val deleteBank = Bank("abc", 2.0, 60)
            //when
            val performPost = mockMvc.delete(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(deleteBank)
            }
            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }
        }

        @Test
        fun `should return bad request if bank doesnot exist`() {
            //given
            val deleteBank = Bank("blah", 2.0, 60)
            //when
            val performPost = mockMvc.delete(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(deleteBank)
            }
            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

}