package com.example.shadeit.Screens.bottom.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shadeit.R
import com.example.shadeit.frontend.common.Card
import com.example.shadeit.navigation.Screen
import com.example.shadeit.session.Manager
import com.example.shadeit.ui.theme.brush2
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppDrawerContent(
    navController: NavController,
    userName: String,
    userEmail: String,
    modifier: Modifier = Modifier
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush2
            )
            .padding(16.dp)
    ) {
        // 👤 User Profile Glass Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "User Icon",
                tint = Color.White,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(userName, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(userEmail, color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)

        }

        Spacer(modifier = Modifier.height(24.dp))
        Divider(color = Color.White.copy(alpha = 0.3f))

        Spacer(modifier = Modifier.height(20.dp))

        // 🧭 Navigation Items
//        DrawerItem("🌐 Language") { /* navigate */ }
//        DrawerItem("🗑️ Trash") { /* navigate */ }
//        DrawerItem("📅 Calendar") { /* navigate */ }
//        DrawerItem("📞 Support") { /* navigate */ }
//        DrawerItem("📈 Analytics") { /* navigate */ }
//        DrawerItem("📈 Trends") { /* navigate */ }
//        DrawerItem("⚙️ Settings") { /* navigate */ }
//        DrawerItem("❓ Help") { /* navigate */ }
//        DrawerItem("📧 Contact Us") { /* navigate */ }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            DrawerItem("🏠 Home") { navController.navigate(Screen.Controller.route) }
            DrawerItem("💡 Tutorials") { /* navigate */ }
            DrawerItem("📖 Privacy Policy") {
                navController.navigate(Screen.PrivacyPolicy.route) {
//                    popUpTo(Screen.Controller.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
            DrawerItem("📜 Terms of Service") { /* navigate */ }
            DrawerItem("🔧 Feedback") { /* navigate */ }
            DrawerItem("ℹ️ About") { /* navigate */ }
            DrawerItem("🔗 Share") { /* navigate */ }
            DrawerItem("Logout") { showLogoutDialog = true }
        }


        Spacer(modifier = Modifier.weight(1f))

        // 🌟 Footer - Made with love
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Made with",
                color = Color.LightGray,
                fontSize = 12.sp
            )
            Text(
                text = " ❤️ ",
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Text(
                text = "by DSB",
                color = Color.LightGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }

        if (showLogoutDialog) {
            LogoutConfirmationDialog(
                onConfirm = {
                    val sessionManager = Manager(navController.context)
                    Firebase.auth.signOut()
                    sessionManager.clearSession()
                    navController.navigate(Screen.AuthScreen.route) {
                        popUpTo(0)
                    }
                    showLogoutDialog = false
                },
                onDismiss = { showLogoutDialog = false }
            )
        }
    }
}

@Composable
fun DrawerItem(title: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(
                onClick = onClick,
                indication = androidx.compose.material3.ripple(
                    bounded = true,
                    color = Color.White
                ),
                interactionSource = interactionSource
            )
            .background(Color.White.copy(alpha = 0.01f))
            .padding(vertical = 14.dp, horizontal = 12.dp)
    ) {
        if(title == "Logout") {
            Icon(painter = painterResource(R.drawable.baseline_logout_24), contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        } else {
            Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}
