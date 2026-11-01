package com.scrumdapp.errorhandlingpackage.testing

import com.scrumdapp.errorhandlingpackage.exceptions.generic.InternalServerErrorException
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import jdk.internal.util.xml.impl.Input
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Controller
class TestController {

    @GetMapping("/exceptionTest")
    fun testException() {
        throw InternalServerErrorException()
    }

    @GetMapping("/pathTest")
    fun testPath(
        @RequestParam(value = "sample", required = true) query: String,
    ): ResponseEntity<String> {
        return ResponseEntity.ok("OK")
    }

    @PostMapping("/posttest")
    fun testInValidation(
        @RequestBody @Valid input: ExamplePost
    ): ResponseEntity<String> {
        return ResponseEntity.ok("OK")
    }
}

data class ExamplePost(
    @Size(min = 1, max = 10, message = "errors.validation.group.length")
    val test: String,

    @Pattern(regexp = "ON_TIME|ABSENT|LATE|VERIFIED_LATE|VERIFIED_ABSENT|ONLINE|SICK", message = "errors.validation.checkpoints.invalid")
    val test2: String,
)