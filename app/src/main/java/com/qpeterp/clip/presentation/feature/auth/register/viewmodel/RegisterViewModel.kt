package com.qpeterp.clip.presentation.feature.auth.register.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.qpeterp.clip.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {
    private val _id = mutableStateOf("")
    val id get() = _id.value

    private val _password = mutableStateOf("")
    val password get() = _password.value

    private val _name = mutableStateOf("")
    val name get() = _name.value

    fun inputId(newId: String) {
        _id.value = newId
    }

    fun inputPassword(newPassword: String) {
        _password.value = newPassword
    }

    fun inputName(newName: String) {
        _name.value = newName
    }

    fun clear() {
        _id.value = ""
        _password.value = ""
        _name.value = ""
    }

    suspend fun register(
        onRegisterSuccess: () -> Unit,
        onRegisterFailure: (String) -> Unit
        ) {
        val result = registerUseCase(
            param = RegisterUseCase.Param(
                email = id,
                password = password,
                name = name
            )
        )

        result.onSuccess {
            if (it.isSuccessful) {
                onRegisterSuccess()
            } else {
                onRegisterFailure(it.message())
            }
        }.onFailure {
            onRegisterFailure(it.message.toString())
        }
    }
}