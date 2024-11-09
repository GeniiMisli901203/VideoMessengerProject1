package com.example.videomessengerproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.videomessengerproject.ui.theme.VideoMessengerProjectTheme


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = UsersAccountsDatabase.getInstance(applicationContext)

        setContent {
            VideoMessengerProjectTheme {
                MyApp(dao)
            }
        }
    }
}

@Composable
fun MyApp(dao: UsersAccountsDatabase) {
    val navController = rememberNavController()
    val viewModelFactory = remember { UsersAccountViewModelFactory(dao) }
    NavHost(navController, startDestination = "StartScreen") {
        composable("StartScreen") { LoginScreen(navController) }
        composable("ScrollLineScreen") { MainScreenScrollLine() }
        composable("RegistrationScreen") { RegistrationScreen(navController, viewModel(factory = viewModelFactory)) }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    val newLogin = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.very_light_for_background)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLogo(R.drawable.app_image1, "Логотип приложения")
        TextFieldLogin(newLogin.value) { newLogin.value = it }
        TextFieldPassword(newPassword.value) { newPassword.value = it }
        LoginButton { navController.navigate("ScrollLineScreen") }
        Text(
            text = stringResource(id = R.string.text_registration_user),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        RegistrationButton { navController.navigate("RegistrationScreen") }
    }
}

@Composable
fun MainScreenScrollLine() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Привет")
    }
}

@Composable
fun RegistrationScreen(navController: NavController, viewModel: UsersAccountViewModel) {
    var userName by remember { mutableStateOf("") }
    var userSecondName by remember { mutableStateOf("") }
    var userThirdName by remember { mutableStateOf("") }
    var userDateOfBirthday by remember { mutableStateOf("") }
    var userCityOfBorn by remember { mutableStateOf("") }
    var userSchool by remember { mutableStateOf("") }
    var userSex by remember { mutableStateOf("") }
    var userLogin by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.very_light_for_background)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.registration_screen_start),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.gold_for_text)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(listOf(
                "Имя" to userName,
                "Фамилия" to userSecondName,
                "Отчество" to userThirdName,
                "Дата рождения" to userDateOfBirthday,
                "Город рождения" to userCityOfBorn,
                "Школа" to userSchool,
                "Пол" to userSex,
                "Логин" to userLogin,
                "Пароль" to userPassword
            )) { (label, value) ->
                TextField(
                    value = value,
                    onValueChange = { newValue ->
                        when (label) {
                            "Имя" -> userName = newValue
                            "Фамилия" -> userSecondName = newValue
                            "Отчество" -> userThirdName = newValue
                            "Дата рождения" -> userDateOfBirthday = newValue
                            "Город рождения" -> userCityOfBorn = newValue
                            "Школа" -> userSchool = newValue
                            "Пол" -> userSex = newValue
                            "Логин" -> userLogin = newValue
                            "Пароль" -> userPassword = newValue
                        }
                    },
                    label = { Text(label) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }

        Button(
            onClick = {
                val user = UsersAccounts(
                    userName = userName,
                    userSecondName = userSecondName,
                    userThirdName = userThirdName,
                    userDateOfBirthday = userDateOfBirthday,
                    userCityOfBorn = userCityOfBorn,
                    userSchool = userSchool,
                    userSex = userSex,
                    userLogin = userLogin,
                    userPassword = userPassword
                )
                viewModel.addUser(user)
                navController.navigate("ScrollLineScreen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Сохранить", fontSize = 16.sp)
        }
    }
}

@Composable
fun AppLogo(image: Int, description: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = description,
            modifier = Modifier
                .padding(36.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(36.dp))
        )
    }
}

@Composable
fun TextFieldLogin(login: String, changed: (String) -> Unit) {
    TextField(
        value = login,
        label = {
            Text(
                text = stringResource(id = R.string.text_field_login_label),
                color = colorResource(id = R.color.gold_for_text)
            )
        },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
        onValueChange = changed,
        modifier = Modifier
            .background(color = colorResource(id = R.color.very_light_for_background))
            .size(
                width = 250.dp,
                height = 50.dp
            )
    )
}

@Composable
fun TextFieldPassword(password: String, changed: (String) -> Unit) {
    TextField(
        value = password,
        label = {
            Text(
                text = stringResource(id = R.string.text_field_password_label),
                color = colorResource(id = R.color.gold_for_text)
            )
        },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "passwordIcon") },
        onValueChange = changed,
        modifier = Modifier
            .padding(vertical = 40.dp)
            .size(
                width = 250.dp,
                height = 50.dp
            )
    )
}

@Composable
fun LoginButton(clicked: () -> Unit) {
    Button(
        onClick = clicked,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF004d00),
            contentColor = colorResource(id = R.color.gold_for_text)
        )
    ) {
        Text(
            text = stringResource(id = R.string.login_button_text)
        )
    }
}

@Composable
fun RegistrationButton(clicked: () -> Unit) {
    Button(
        onClick = clicked,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF004d00),
            contentColor = colorResource(id = R.color.gold_for_text)
        )
    ) {
        Text(
            text = stringResource(id = R.string.registration_button_text)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MaAppPreview() {
    val dao = UsersAccountsDatabase.getInstance(LocalContext.current).userAccountsDao
    //val viewModelFactory = UsersAccountViewModelFactory(dao)
    //RegistrationScreen(navController = rememberNavController(), viewModel(factory = viewModelFactory))
}

