package it.pierosilvestri.leaderboard_presentation.leaderboard.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import it.pierosilvestri.core.domain.model.Player
import it.pierosilvestri.core.domain.model.PlayerPictures
import it.pierosilvestri.core_ui.components.PulseAnimation
import it.pierosilvestri.leaderboard_presentation.R

@Composable
fun PlayerListItem(
    player: Player,
    onClick: (Player) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .clickable { onClick(player) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        var imageLoadResult by remember {
            mutableStateOf<Result<Painter>?>(null)
        }
        val painter = rememberAsyncImagePainter(
            model = player.pictures?.smallPicture,
            onSuccess = {
                imageLoadResult =
                    if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                        Result.success(it.painter)
                    } else {
                        Result.failure(Exception("Invalid image size"))
                    }
            },
            onError = {
                it.result.throwable.printStackTrace()
                imageLoadResult = Result.failure(it.result.throwable)
            }
        )

        when (val result = imageLoadResult) {
            null -> PulseAnimation(
                modifier = Modifier.size(60.dp)
            )

            else -> {
                Image(
                    painter = if (result.isSuccess) painter else {
                        painterResource(R.drawable.boy)
                    },
                    contentDescription = player.fullname,
                    contentScale = if (result.isSuccess) {
                        ContentScale.Crop
                    } else {
                        ContentScale.Fit
                    },
                    modifier = Modifier
                        .size(60.dp)

                        .clip(CircleShape)
                )
            }
        }
        Text(
            text = player.fullname,
            modifier = Modifier.padding(start = 16.dp),
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Preview
@Composable
fun PreviewPlayerListItem() {
    val player = Player(
        id = "1",
        fullname = "John Doe",
        pictures = PlayerPictures(
            largePicture = "https://randomuser.me/api/portraits/men/91.jpg",
            mediumPicture = "https://randomuser.me/api/portraits/men/91.jpg",
            smallPicture = "https://randomuser.me/api/portraits/men/91.jpg",
        ),
        sessions = emptyList()
    )
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            LazyColumn {
                items(4) {
                    PlayerListItem(player = player)
                }
            }
        }
    }
}