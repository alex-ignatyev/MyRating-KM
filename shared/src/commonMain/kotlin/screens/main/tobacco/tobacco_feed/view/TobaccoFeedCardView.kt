package screens.main.tobacco.tobacco_feed.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.my_rating.shared.images.AppResImages
import io.github.skeptick.libres.compose.painterResource
import io.github.skeptick.libres.images.Image
import model.domain.TobaccoFeed
import ui.KalyanTheme
import ui.components.KalyanImage

@Composable
fun TobaccoView(tobaccoFeed: TobaccoFeed, position: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = position.toString(),
            style = KalyanTheme.typography.header,
            textAlign = TextAlign.Center
        )

        Card(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
            colors = CardDefaults.cardColors(containerColor = KalyanTheme.colors.background),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth().fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {

                KalyanImage(tobaccoFeed.image, modifier = Modifier.padding(8.dp))

                Column(modifier = Modifier.wrapContentWidth()) {
                    Text(
                        text = tobaccoFeed.taste,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = KalyanTheme.colors.backgroundOn
                    )
                    Text(text = tobaccoFeed.company, fontSize = 12.sp, color = KalyanTheme.colors.backgroundOn)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Values(
                        value = tobaccoFeed.rating.toString(),
                        image = AppResImages.ic_star,
                        contentDescription = "Rating",
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Values(
                        value = tobaccoFeed.votes.toString(),
                        image = AppResImages.ic_views,
                        contentDescription = "Views",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Values(value: String, image: Image, contentDescription: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = KalyanTheme.typography.hint,
            color = KalyanTheme.colors.backgroundOn,
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(image),
            contentDescription = contentDescription,
            modifier = Modifier.size(16.dp)
        )
    }
}
