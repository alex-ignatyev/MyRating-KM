package screens.main.category.category_add

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
import com.moriatsushi.insetsx.ExperimentalSoftwareKeyboardApi
import com.moriatsushi.insetsx.ime
import com.moriatsushi.insetsx.navigationBars
import com.moriatsushi.insetsx.statusBars
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import model.domain.Company
import screens.main.category.category_add.AddCategoryAction.AddCategoryClick
import screens.main.category.category_add.AddCategoryAction.ChangeCompany
import screens.main.category.category_add.AddCategoryAction.ChangeLine
import screens.main.category.category_add.AddCategoryAction.ChangeManual
import screens.main.category.category_add.AddCategoryAction.ChangeStrengthByCompany
import screens.main.category.category_add.AddCategoryAction.ChangeTaste
import screens.main.category.category_add.AddCategoryAction.OnBackClick
import screens.main.category.category_add.AddCategoryAction.OnCompanyClick
import screens.main.category.category_add.AddCategoryAction.OnLineClick
import ui.KalyanTheme
import ui.components.KalyanButton
import ui.components.KalyanCircularProgress
import ui.components.KalyanSelect
import ui.components.KalyanTextField
import ui.components.KalyanToolbar

@Composable
fun AddTobaccoView(state: AddCategoryState, modifier: Modifier = Modifier, doAction: (AddCategoryAction) -> Unit) {

    val tobacco = state.tobacco

    Scaffold(
        modifier = modifier.background(KalyanTheme.colors.background)
            .windowInsetsPadding(WindowInsets.statusBars),
        backgroundColor = KalyanTheme.colors.background,
        topBar = {
            KalyanToolbar(
                title = AppResStrings.title_admin_add_tobacco,
                onBackClick = {
                    doAction(OnBackClick)
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
                        color = KalyanTheme.colors.backgroundOn,
                        modifier = Modifier.weight(1f)
                    )

                    Switch(state.isManual, colors = colors(
                        checkedThumbColor = KalyanTheme.colors.primary,
                        checkedTrackColor = KalyanTheme.colors.backgroundOn,
                        uncheckedTrackColor = KalyanTheme.colors.backgroundOn
                    ), onCheckedChange = {
                        doAction(ChangeManual(it))
                    })
                }

                if (state.isManual) {
                    KalyanTextField(
                        value = tobacco.company,
                        placeholder = AppResStrings.text_company,
                        enabled = !state.isLoading,
                        isError = state.error.isNotBlank()
                    ) {
                        doAction(ChangeCompany(it))
                    }
                } else {
                    KalyanSelect(title = AppResStrings.text_company, text = tobacco.company) {
                        doAction(OnCompanyClick)
                    }
                }

                KalyanTextField(
                    value = tobacco.taste,
                    placeholder = AppResStrings.text_taste,
                    enabled = !state.isLoading,
                    isError = state.error.isNotBlank(),
                ) {
                    doAction(ChangeTaste(it))
                }

                if (state.isManual) {
                    KalyanTextField(
                        value = tobacco.line,
                        placeholder = AppResStrings.text_line,
                        enabled = !state.isLoading,
                        isError = state.error.isNotBlank()
                    ) {
                        doAction(ChangeLine(it))
                    }
                } else {
                    KalyanSelect(title = AppResStrings.text_line, text = tobacco.line) {
                        val lines = state.companies.findLast { it.company == tobacco.company }?.lines ?: return@KalyanSelect
                        doAction(OnLineClick(lines))
                    }
                }

                KalyanTextField(
                    value = tobacco.strength,
                    placeholder = AppResStrings.text_strength,
                    enabled = !state.isLoading,
                    inputType = KeyboardType.NumberPassword,
                    isError = state.error.isNotBlank()
                ) {
                    doAction(ChangeStrengthByCompany(it))
                }

                Text(
                    text = state.error,
                    color = KalyanTheme.colors.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            KalyanButton(
                modifier = Modifier.padding(vertical = 16.dp).align(Alignment.BottomCenter),
                text = if (state.isLoading) null else AppRes.string.title_admin_add_tobacco,
                enabled = !state.isLoading && state.isButtonEnabled,
                content = {
                    KalyanCircularProgress()
                },
                onClick = {
                    doAction(AddCategoryClick)
                }
            )
        }
    }
}

data class CompanyBottomSheet(val companies: List<Company>, val doAction: (AddCategoryAction) -> Unit) : Screen {

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
                        doAction(ChangeCompany(it.company))
                        bottomSheetNavigator.hide()
                    })
            }
        }
    }
}

data class LineBottomSheet(val lines: List<String>, val doAction: (AddCategoryAction) -> Unit) : Screen {

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
                        doAction(ChangeLine(it))
                        bottomSheetNavigator.hide()
                    })
            }
        }
    }
}
