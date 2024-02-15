package screens.main.category.category_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.my_rating.shared.images.AppResImages
import com.my_rating.shared.strings.AppResStrings
import com.moriatsushi.insetsx.navigationBars
import com.moriatsushi.insetsx.statusBars
import io.github.skeptick.libres.compose.painterResource
import io.github.skeptick.libres.images.Image
import model.data.tobacco.TobaccoVoteRequest.VoteType.Aroma
import model.data.tobacco.TobaccoVoteRequest.VoteType.Rating
import model.data.tobacco.TobaccoVoteRequest.VoteType.Smokiness
import model.data.tobacco.TobaccoVoteRequest.VoteType.Strength
import model.data.tobacco.TobaccoVoteRequest.VoteType.Taste
import screens.main.category.category_info.TobaccoInfoEvent.OnBackClick
import screens.main.category.category_info.view.VoteBottomSheet
import ui.KalyanTheme
import ui.components.KalyanDivider
import ui.components.KalyanImage
import ui.components.KalyanToolbar
import ui.components.android.AndroidBottomBarHeight

@Composable
internal fun TobaccoInfoView(state: TobaccoInfoState, obtainEvent: (TobaccoInfoEvent) -> Unit) {

    val data = state.data

    Scaffold(
        modifier = Modifier
            .background(KalyanTheme.colors.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars.add(WindowInsets(bottom = AndroidBottomBarHeight))),
        backgroundColor = KalyanTheme.colors.background,
        topBar = {
            KalyanToolbar(
                title = AppResStrings.title_category_info,
                isTransparent = true,
                onBackClick = {
                    obtainEvent(OnBackClick())
                })
        }
    ) {
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            TobaccoBlock(state = state, modifier = Modifier.fillMaxWidth()) {

            }

            InfoBlock(state = state, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {

            }

            Text(
                text = "Оценки пользователей",
                style = KalyanTheme.typography.caption,
                color = KalyanTheme.colors.secondaryText,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            KalyanDivider(modifier = Modifier.fillMaxWidth(0.5f).padding(start = 16.dp))

            RatingInfoUsers(
                data.strengthByUsers.toString(),
                data.smokinessByUsers.toString(),
                data.aromaByUsers.toString(),
                data.tasteByUsers.toString(),
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Ваши оценки",
                style = KalyanTheme.typography.caption,
                color = KalyanTheme.colors.secondaryText,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            KalyanDivider(modifier = Modifier.fillMaxWidth(0.5f).padding(start = 16.dp))

            RatingInfoUser(
                ratingByUser = data.ratingByUser,
                strengthByUser = data.strengthByUser,
                smokinessByUser = data.smokinessByUser,
                aromaByUser = data.aromaByUser,
                tasteByUser = data.tasteByUser,
                obtainEvent = {}
            )
        }
    }
}

@Composable
fun RatingInfoUsers(
    strengthByUsers: String,
    smokinessByUsers: String,
    aromaByUsers: String,
    tasteByUsers: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RatingValue(
                value = strengthByUsers,
                title = AppResStrings.text_strength,
                modifier = Modifier.weight(1f)
            ) {

            }

            RatingValue(
                value = smokinessByUsers,
                title = AppResStrings.text_smokiness,
                modifier = Modifier.weight(1f)
            ) {

            }
        }

        Row(modifier = Modifier.padding(vertical = 16.dp)) {
            RatingValue(
                value = aromaByUsers,
                title = AppResStrings.text_aroma,
                modifier = Modifier.weight(1f)
            ) {

            }

            RatingValue(
                value = tasteByUsers,
                title = AppResStrings.text_taste,
                modifier = Modifier.weight(1f)
            ) {

            }
        }
    }
}

@Composable
fun RatingInfoUser(
    ratingByUser: Long,
    strengthByUser: Long,
    smokinessByUser: Long,
    aromaByUser: Long,
    tasteByUser: Long,
    obtainEvent: (TobaccoInfoEvent) -> Unit
) {
    val bottomSheetNavigator = LocalBottomSheetNavigator.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "${AppResStrings.text_rating}: $ratingByUser",
            style = KalyanTheme.typography.caption,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp).wrapContentHeight().clickable {
                bottomSheetNavigator.show(VoteBottomSheet(Rating, ratingByUser, obtainEvent))
            }
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${AppResStrings.text_strength}: $strengthByUser",
                style = KalyanTheme.typography.caption,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp).wrapContentHeight().weight(1f).clickable {
                    bottomSheetNavigator.show(VoteBottomSheet(Strength, strengthByUser, obtainEvent))
                }
            )

            Text(
                text = "${AppResStrings.text_smokiness}: $smokinessByUser",
                style = KalyanTheme.typography.caption,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp).wrapContentHeight().weight(1f).clickable {
                    bottomSheetNavigator.show(VoteBottomSheet(Smokiness, smokinessByUser, obtainEvent))
                }
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${AppResStrings.text_aroma}: $aromaByUser",
                style = KalyanTheme.typography.caption,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp).wrapContentHeight().weight(1f).clickable {
                    bottomSheetNavigator.show(VoteBottomSheet(Aroma, aromaByUser, obtainEvent))
                }
            )

            Text(
                text = "${AppResStrings.text_taste}: $tasteByUser",
                style = KalyanTheme.typography.caption,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp).wrapContentHeight().weight(1f).clickable {
                    bottomSheetNavigator.show(VoteBottomSheet(Taste, tasteByUser, obtainEvent))
                }
            )
        }
    }
}

@Composable
fun TobaccoBlock(state: TobaccoInfoState, modifier: Modifier = Modifier, obtainEvent: (TobaccoInfoEvent) -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KalyanImage(
            state.data.image,
            modifier = Modifier.padding(top = 16.dp).clip(CircleShape),
            size = 160,
            contentScale = ContentScale.Inside
        )

        Text(
            text = state.data.taste,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp).wrapContentHeight()
        )

        Row(
            modifier = Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = state.data.company,
                style = KalyanTheme.typography.caption,
                color = KalyanTheme.colors.secondaryText
            )

            Text(
                text = " / ",
                style = KalyanTheme.typography.caption,
                color = KalyanTheme.colors.secondaryText
            )

            Text(
                text = state.data.line,
                style = KalyanTheme.typography.caption,
                color = KalyanTheme.colors.secondaryText
            )
        }
    }
}

@Composable
fun InfoBlock(state: TobaccoInfoState, modifier: Modifier = Modifier, obtainEvent: (TobaccoInfoEvent) -> Unit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RatingBlockValue(state.data.ratingByUsers.toString(), state.data.votes.toString(), modifier = Modifier.weight(1f)) {}
        RatingValueImage(state.data.commentsSize.toString(), AppResImages.ic_comments, modifier = Modifier.weight(1f)) {}
        RatingValueImage(state.data.strength.toString(), AppResImages.ic_strentgh, modifier = Modifier.weight(1f)) {}
    }
}

@Composable
fun RatingValue(value: String, title: String, modifier: Modifier = Modifier, obtainEvent: (TobaccoInfoEvent) -> Unit) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            style = KalyanTheme.typography.caption
        )

        Text(
            text = title,
            fontSize = 12.sp,
            style = KalyanTheme.typography.caption,
            color = KalyanTheme.colors.secondaryText
        )
    }
}

@Composable
fun RatingBlockValue(
    ratingByUsers: String,
    votes: String,
    modifier: Modifier = Modifier,
    obtainEvent: (TobaccoInfoEvent) -> Unit
) {
    Column(
        modifier = modifier.clickable {

        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = ratingByUsers,
                fontSize = 18.sp,
                style = KalyanTheme.typography.caption,
                color = KalyanTheme.colors.backgroundOn,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "($votes)",
                fontSize = 18.sp,
                style = KalyanTheme.typography.caption,
                color = KalyanTheme.colors.secondaryText
            )
        }

        Image(
            painter = painterResource(AppResImages.ic_star),
            contentDescription = "", //TODO,
            colorFilter = ColorFilter.tint(KalyanTheme.colors.secondaryText),
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
fun RatingValueImage(
    value: String,
    image: Image,
    contentDescription: String = "",
    modifier: Modifier = Modifier,
    obtainEvent: (TobaccoInfoEvent) -> Unit
) {
    Column(
        modifier = modifier.clickable {

        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 18.sp,
            style = KalyanTheme.typography.hint,
            color = KalyanTheme.colors.backgroundOn,
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(image),
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(KalyanTheme.colors.secondaryText),
            modifier = Modifier.size(36.dp)
        )
    }
}
