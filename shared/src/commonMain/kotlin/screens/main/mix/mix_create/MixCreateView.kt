package screens.main.mix.mix_create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import com.moriatsushi.insetsx.ExperimentalSoftwareKeyboardApi
import com.moriatsushi.insetsx.ime
import com.moriatsushi.insetsx.navigationBars
import com.moriatsushi.insetsx.statusBars
import model.domain.Company
import screens.main.mix.mix_create.MixCreateEvent.AddTobaccoClick
import screens.main.mix.mix_create.MixCreateEvent.ChangeCompany
import screens.main.mix.mix_create.MixCreateEvent.ChangeLine
import screens.main.mix.mix_create.MixCreateEvent.ChangeManual
import screens.main.mix.mix_create.MixCreateEvent.ChangeStrengthByCompany
import screens.main.mix.mix_create.MixCreateEvent.ChangeTaste
import screens.main.mix.mix_create.MixCreateEvent.OnBackClick
import screens.main.mix.mix_create.MixCreateEvent.OnCompanyClick
import screens.main.mix.mix_create.MixCreateEvent.OnLineClick
import ui.KalyanTheme
import ui.components.KalyanButton
import ui.components.KalyanCircularProgress
import ui.components.KalyanSelect
import ui.components.KalyanTextField
import ui.components.KalyanToolbar

@OptIn(ExperimentalSoftwareKeyboardApi::class)
@Composable
fun MixCreateView(state: MixCreateState, obtainEvent: (MixCreateEvent) -> Unit) {

    Scaffold(
        modifier = Modifier.background(KalyanTheme.colors.primary)
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.ime),
        backgroundColor = KalyanTheme.colors.primary,
        topBar = {
            KalyanToolbar(
                title = AppResStrings.title_admin_add_tobacco,
                onBackClick = {
                    obtainEvent(OnBackClick())
                })
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = AppResStrings.text_admin_manually,
                        style = KalyanTheme.typography.header,
                        color = KalyanTheme.colors.primaryOn,
                        modifier = Modifier.weight(1f)
                    )

                    Switch(state.isManual, colors = colors(
                        checkedThumbColor = KalyanTheme.colors.primary,
                        checkedTrackColor = KalyanTheme.colors.primaryOn,
                        uncheckedTrackColor = KalyanTheme.colors.primaryOn
                    ), onCheckedChange = {
                        obtainEvent(ChangeManual(it))
                    })
                }

                if (state.isManual) {
                    KalyanTextField(
                        value = state.company,
                        placeholder = AppResStrings.text_company,
                        enabled = !state.isLoading,
                        isError = state.error.isNotBlank()
                    ) {
                        obtainEvent(ChangeCompany(it))
                    }
                } else {
                    KalyanSelect(title = AppResStrings.text_company, text = state.company) {
                        obtainEvent(OnCompanyClick())
                    }
                }

                KalyanTextField(
                    value = state.taste,
                    placeholder = AppResStrings.text_taste,
                    enabled = !state.isLoading,
                    isError = state.error.isNotBlank(),
                ) {
                    obtainEvent(ChangeTaste(it))
                }

                if (state.isManual) {
                    KalyanTextField(
                        value = state.line,
                        placeholder = AppResStrings.text_line,
                        enabled = !state.isLoading,
                        isError = state.error.isNotBlank()
                    ) {
                        obtainEvent(ChangeLine(it))
                    }
                } else {
                    KalyanSelect(title = AppResStrings.text_line, text = state.line) {
                        val lines = state.companies.findLast { it.company == state.company }?.lines ?: return@KalyanSelect
                        obtainEvent(OnLineClick(lines))
                    }
                }

                KalyanTextField(
                    value = state.strength,
                    placeholder = AppResStrings.text_strength,
                    enabled = !state.isLoading,
                    inputType = KeyboardType.NumberPassword,
                    isError = state.error.isNotBlank()
                ) {
                    obtainEvent(ChangeStrengthByCompany(it))
                }

                Text(
                    text = state.error,
                    color = KalyanTheme.colors.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            KalyanButton(
                modifier = Modifier.padding(vertical = 32.dp).align(Alignment.BottomCenter)
                    .windowInsetsPadding(WindowInsets.navigationBars.add(WindowInsets.navigationBars)),
                text = if (state.isLoading) null else AppRes.string.title_admin_add_tobacco,
                enabled = !state.isLoading && state.isButtonEnabled,
                content = {
                    KalyanCircularProgress()
                },
                onClick = {
                    obtainEvent(AddTobaccoClick())
                }
            )
        }
    }
}

data class CompanyBottomSheet(val companies: List<Company>, val obtainEvent: (MixCreateEvent) -> Unit) : Screen {

    @OptIn(ExperimentalSoftwareKeyboardApi::class)
    @Composable
    override fun Content() {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        LazyColumn(
            modifier = Modifier.wrapContentHeight()
                .windowInsetsPadding(WindowInsets.navigationBars.add(WindowInsets.navigationBars).add(WindowInsets(bottom = 8.dp)))
                .windowInsetsPadding(WindowInsets.ime)
        ) {
            items(companies) {
                Text(
                    text = it.company,
                    style = KalyanTheme.typography.body,
                    modifier = Modifier.fillMaxWidth().clickable {
                        obtainEvent(ChangeCompany(it.company))
                        bottomSheetNavigator.hide()
                    })
            }
        }
    }
}

data class LineBottomSheet(val lines: List<String>, val obtainEvent: (MixCreateEvent) -> Unit) : Screen {

    @OptIn(ExperimentalSoftwareKeyboardApi::class)
    @Composable
    override fun Content() {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        LazyColumn(
            modifier = Modifier.wrapContentHeight()
                .windowInsetsPadding(WindowInsets.navigationBars.add(WindowInsets.navigationBars).add(WindowInsets(bottom = 8.dp)))
                .windowInsetsPadding(WindowInsets.ime)
        ) {
            items(lines) {
                Text(
                    text = it,
                    style = KalyanTheme.typography.body,
                    modifier = Modifier.fillMaxWidth().clickable {
                        obtainEvent(ChangeLine(it))
                        bottomSheetNavigator.hide()
                    })
            }
        }
    }
}
