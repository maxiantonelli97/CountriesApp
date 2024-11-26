package com.antonelli.countriesapp.ui.detail

import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.antonelli.countriesapp.R
import com.antonelli.countriesapp.entity.CountryModel
import kotlinx.coroutines.launch

@Composable
fun detailScreen(
    navController: NavController,
    country: CountryModel?,
) {
    val scope = rememberCoroutineScope()

    // https://github.com/androidx/androidx/blob/androidx-main/compose/docs/compose-component-api-guidelines.md
    /*
        Recomposition
        https://developer.android.com/develop/ui/compose/documentation
        https://www.youtube.com/watch?v=JvbyGcqdWBA
        https://www.youtube.com/watch?v=6_wK_Ud8--0
        https://manuelvivo.dev/consuming-flows-compose
        https://manuelvivo.dev/coroutines-addrepeatingjob
     */

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->

            val view =
                LayoutInflater.from(context).inflate(R.layout.detail_screen_layout, null, false)

            val loader = ImageLoader(context)
            val flag = ImageRequest.Builder(context)
                .data(country?.flags?.png)
                .crossfade(true)
                .build()

            val arms = ImageRequest.Builder(context)
                .data(country?.coatOfArms?.png)
                .crossfade(true)
                .build()

            scope.launch {
                val resultFlag = (loader.execute(flag) as? SuccessResult)?.drawable
                val bitmapFlag = (resultFlag as? BitmapDrawable)?.bitmap

                if (bitmapFlag != null) {
                    view.findViewById<ImageView>(R.id.iv_flag).setImageBitmap(bitmapFlag)
                }

                val resultArm = (loader.execute(arms) as? SuccessResult)?.drawable
                val bitmapArm = (resultArm as? BitmapDrawable)?.bitmap

                if (bitmapArm != null) {
                    view.findViewById<ImageView>(R.id.iv_coat).setImageBitmap(bitmapArm)
                }
            }

            view.findViewById<TextView>(R.id.tv_capital).text = country?.capital?.get(0) ?: ""
            view.findViewById<TextView>(R.id.tv_tittle).text = country?.name?.common ?: ""
            view.findViewById<TextView>(R.id.tv_name_common).text = country?.name?.common ?: ""
            view.findViewById<TextView>(R.id.tv_name_oficial).text = country?.name?.official ?: ""
            view.findViewById<TextView>(R.id.tv_region).text = country?.region ?: ""
            view.findViewById<TextView>(R.id.tv_subregion).text = country?.subregion ?: ""
            var langAux = ""
            val cantLang = country?.languages?.values?.size ?: 1
            var contador = 1
            country?.languages?.forEach { l ->
                if (cantLang > 1) {
                    if (cantLang != contador) {
                        langAux += l.value + ", "
                        contador++
                    } else {
                        langAux += l.value
                        contador++
                    }
                } else {
                    langAux += l.value
                }
            }
            view.findViewById<TextView>(R.id.tv_lang).text = langAux

            var currAux = ""
            val cantCurr = country?.currencies?.values?.size ?: 1
            contador = 0
            country?.currencies?.forEach { c ->
                if (cantCurr > 1) {
                    if (cantCurr != contador) {
                        currAux += c.key + " (${c.value.name})" + ", "
                        contador++
                    } else {
                        currAux += c.key + "(${c.value.name})"
                        contador++
                    }
                } else {
                    currAux += c.key + "(${c.value.name})"
                }
            }

            view.findViewById<TextView>(R.id.tv_currencies).text = currAux

            view.findViewById<TextView>(R.id.tv_population).text = country?.population.toString()
            view.findViewById<TextView>(R.id.tv_car).text = country?.car?.side

            view.findViewById<LinearLayout>(R.id.ll_back).setOnClickListener {
                navController.popBackStack()
            }
            view
        }
    )
}
