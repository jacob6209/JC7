package com.example.jc7

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.request.RequestOptions
import com.example.jc7.enums.ConnectionStatus
import com.example.jc7.ui.components.AppButton
import com.example.jc7.ui.theme.AppBackground1
import com.example.jc7.ui.theme.AppBackground2
import com.example.jc7.ui.theme.AppDarkOpacity
import com.example.jc7.ui.theme.AppWhiteOpacity
import com.example.jc7.ui.theme.JC7Theme
import com.skydoves.landscapist.glide.GlideImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JC7Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView()
                }
            }
        }
    }
}

@Composable
fun MainView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        AppBackground1, AppBackground2
                    )
                )
            )
    ) {
        Column(Modifier.fillMaxSize()) {
            TopHeaser()
            Spacer(modifier = Modifier.height(20.dp))
            ConnectionButton()
            Spacer(modifier = Modifier.height(25.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
            ) {
                AppButton(R.drawable.openvpn,"Open Vpn","Select Protocol")
                Spacer(modifier = Modifier.height(15.dp))
                AppButton(R.drawable.iran_p,"Iran","Tehran")

            }

        }
    }

}


@Composable
private fun ConnectionButton() {
    var status by remember { mutableStateOf(ConnectionStatus.Disconnected) }
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
        ) {
            when (status) {
                ConnectionStatus.Disconnected -> {
                    Icon(
                        painter = painterResource(id = R.drawable.circle),
                        contentDescription = "",
                        Modifier
                            .size(250.dp)
                            .align(Alignment.Center),
                        tint = AppWhiteOpacity
                    )
                }

                ConnectionStatus.Connecting -> {
                    GlideImage(
                        imageModel = R.drawable.connecting,
                        contentScale = ContentScale.Crop,
                        requestOptions = RequestOptions().centerCrop(),
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(330.dp)
                    )

                }

                ConnectionStatus.Connected -> {
                    GlideImage(
                        imageModel = R.drawable.connected,
                        contentScale = ContentScale.Crop,
                        requestOptions = RequestOptions().centerCrop(),
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(400.dp)
                    )
                }
            }
            IconButton(modifier = Modifier.align(Alignment.Center),
                enabled = status == ConnectionStatus.Connected || status == ConnectionStatus.Disconnected,
                onClick = {
                    status = when (status) {
                        ConnectionStatus.Disconnected -> ConnectionStatus.Disconnected
                        ConnectionStatus.Connecting -> ConnectionStatus.Connecting
                        ConnectionStatus.Connected -> ConnectionStatus.Disconnected
                    }
                    if (status == ConnectionStatus.Connecting) {
                        Handler(Looper.getMainLooper()).postDelayed(Runnable {
                            status = ConnectionStatus.Connected
                        }, 3000)

                    }
                }

            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.power),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(150.dp)
                )

            }
        }
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            var icon = when (status) {
                ConnectionStatus.Disconnected -> R.drawable.ic_baseline_gpp_bad_24
                ConnectionStatus.Connecting -> R.drawable.ic_baseline_sync_24
                ConnectionStatus.Connected -> R.drawable.ic_baseline_gpp_good_24
            }
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                Modifier.size(25.dp),
                tint = AppWhiteOpacity
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = status.name, color = AppWhiteOpacity, fontSize = 21.sp)
        }
    }
}

@Composable
private fun TopHeaser() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(25.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedButton(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(
                1.dp,
                AppWhiteOpacity
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            modifier = Modifier.size(60.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_dashboard),
                contentDescription = "",
                tint = Color.White,
            )
        }
        OutlinedButton(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(
                1.dp,
                AppWhiteOpacity
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            modifier = Modifier.size(60.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = "",
                tint = Color.White
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JC7Theme {
        MainView()
    }
}