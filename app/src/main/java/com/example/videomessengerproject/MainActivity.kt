package com.example.videomessengerproject

import android.icu.number.Scale
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.videomessengerproject.ui.theme.VideoMessengerProjectTheme
import java.io.ByteArrayOutputStream

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
    NavHost(navController, startDestination = "LoginScreen") {
        composable("LoginScreen") { LoginScreen(navController) }
        composable("MainScreen") { MainScreenScrollLine(navController) }
        composable("ChatScreen") { ChatScreen(navController) }
        composable("AccountScreen") { AccountScreen(navController) }
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
        LoginButton { navController.navigate("MainScreen") }
        Text(
            text = stringResource(id = R.string.text_registration_user),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        RegistrationButton { navController.navigate("RegistrationScreen") }
    }
}


@Composable
fun MainScreenScrollLine(navController: NavController) {
    val userAvatar = painterResource(id = R.drawable.avatar_icon)
    val videoContent = painterResource(id = R.drawable.seva_image)
    var showCommentDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { AppBar(userAvatar = userAvatar, navController = navController) },
        bottomBar = { BottomBar(navController) },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.green_for_background))
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(100) { index ->
                    CardItem(
                        userAvatar = userAvatar,
                        userName = "User $index",
                        postDescription = "This is a post description for user $index",
                        videoContent = videoContent,
                        onCommentClick = { showCommentDialog = true } // Обработчик нажатия на иконку комментариев
                    )
                }
            }

            // Показать диалог комментариев
            if (showCommentDialog) {
                CommentDialog(
                    onCommentSubmit = { comment ->
                        // Здесь можно обработать отправку комментария
                        showCommentDialog = false
                    },
                    onDismiss = { showCommentDialog = false }
                )
            }
        }
    )
}


@Composable
fun ChatScreen(navController: NavController) {
    val userAvatar = painterResource(id = R.drawable.avatar_icon)

    Scaffold(
        topBar = { AppBar(userAvatar = userAvatar, navController = navController) },
        bottomBar = { BottomBar(navController) },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.green_for_background))
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Chat Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
fun AccountScreen(navController: NavController) {
    val userAvatar = painterResource(id = R.drawable.avatar_icon)

    Scaffold(
        topBar = { AppBar(userAvatar = userAvatar, navController = navController) },
        bottomBar = { BottomBar(navController) },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.green_for_background))
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Account Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(36.dp)),
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    IconButton(onClick = { navController.navigate("MainScreen") }) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Лента")

                    }
                    Text(text = "Лента", fontSize = 12.sp)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    IconButton(onClick = { navController.navigate("ChatScreen") }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Чаты")
                    }
                    Text(text = "Чаты", fontSize = 12.sp)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    IconButton(onClick = { navController.navigate("AccountScreen") }) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Аккаунт")
                    }
                    Text(text = "Аккаунт", fontSize = 12.sp)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(userAvatar: Painter, navController: NavController) {
    TopAppBar(
        modifier = Modifier.clip(RoundedCornerShape(36.dp)),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = { navController.navigate("AccountScreen") },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                ) {
                    Icon(painter = userAvatar, contentDescription = null)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "App Name", color = colorResource(id = R.color.gold_for_text))
            }
        },
        actions = {
            IconButton(onClick = { /* Handle settings click */ }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
            }
        }
    )
}


@Composable
fun CardItem(userAvatar: Painter, userName: String, postDescription: String, videoContent: Painter, onCommentClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(36.dp)),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = userAvatar,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(36.dp)),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = userName, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.gold_for_text))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = postDescription, color = colorResource(id = R.color.gold_for_text))
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = videoContent,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /* Handle like click */ }) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Like")
                }
                IconButton(onClick = onCommentClick) { // Обработчик нажатия на комментарий
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "Comment")
                }
            }
        }
    }
}

@Composable
fun CommentDialog(onCommentSubmit: (String) -> Unit, onDismiss: () -> Unit) {
    var commentText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Добавить комментарий") },
        text = {
            Column {
                TextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Напишите комментарий...") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onCommentSubmit(commentText)
                commentText = ""
            }) {
                Text("Отправить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
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
    var userAvatarUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        userAvatarUri = uri
    }

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

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                        .clickable { launcher.launch("image/*") }
                        .align(Alignment.CenterHorizontally)
                ) {
                    userAvatarUri?.let { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(model = uri),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    } ?: run {
                        Image(
                            painter = painterResource(id = R.drawable.avatar_icon),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }


            item {
                Button(
                    onClick = {
                        val userAvatar = userAvatarUri?.let { uri ->
                            val inputStream = context.contentResolver.openInputStream(uri)
                            val byteArrayOutputStream = ByteArrayOutputStream()
                            inputStream?.copyTo(byteArrayOutputStream)
                            byteArrayOutputStream.toByteArray()
                        }

                        val user = UsersAccounts(
                            userName = userName,
                            userSecondName = userSecondName,
                            userThirdName = userThirdName,
                            userDateOfBirthday = userDateOfBirthday,
                            userCityOfBorn = userCityOfBorn,
                            userSchool = userSchool,
                            userSex = userSex,
                            userLogin = userLogin,
                            userPassword = userPassword,
                            userAvatar = userAvatar
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
