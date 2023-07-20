package com.example.android_base.utils

import android.widget.Toast

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.google.android.material.button.MaterialButton
import java.io.Serializable
import java.math.BigDecimal
import java.util.Locale


import com.example.android_base.R

fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context, this.toString(), duration).apply { show() }
}

fun Context.show(): Dialog {
    val dialog = Dialog(this)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    val inflater = LayoutInflater.from(this)
    val view = inflater.inflate(R.layout.dialog_loader, null)
    dialog.setContentView(view)
    dialog.setCancelable(false)
    dialog.show()
    return dialog
}

fun Dialog.dismiss() {
    if (this.isShowing) {
        this.dismiss()
    }
}

inline fun <E : Any, T : Collection<E>> T?.withNotNullNorEmpty(func: T.() -> Unit) {
    if (this != null && this.isNotEmpty()) {
        with(this) { func() }
    }
}

inline fun <E : Any, T : Collection<E?>> T?.whenNotNullNorEmpty(
    func: (T) -> Unit,
    elsePath: () -> Unit
) {
    if (this != null && this.isNotEmpty()) {
        func(this)
    } else {
        elsePath()
    }
}

inline var View.leftPaddingDp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        paddingRight.toFloat(),
        resources.displayMetrics
    )
    set(value) {
        val leftPx = resources.displayMetrics.density * value
        setPadding(leftPx.toInt(), paddingTop, paddingRight, paddingBottom)
    }


inline fun <T> T?.notNull(f: (T) -> Unit) {
    if (this != null) {
        f(this)
    }
}

inline fun <T> T?.ifNull(f: (T?) -> Unit) {
    if (this == null) {
        f(this)
    }
}

inline fun String.stringWithoutZeroFraction(): String {
    val bigDecimal = BigDecimal(this)
    val stripped = bigDecimal.stripTrailingZeros()
    return stripped.toPlainString()
}

/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/

/**
 * To get desired value for given VALUE whether it is null or not
 * Usage :
 *      any_kind_of_value.ifNotNullOrElse({ your_return_value_if_given_value_is_not_null }, { your_return_value_if_given_value_is_null })
 * */

inline fun <T : Any, R> T?.ifNotNullOrElse(ifNotNullPath: (T) -> R, elsePath: () -> R) =
    let { if (it == null) elsePath() else ifNotNullPath(it) }


/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/

/**
 * To visible and gone view
 * Usage :
 *      to Visible view : yourView.visible()
 *      to Gone view : yourView.gone()
 *      to Invisible view : yourView.invisible()
 * */
internal fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.setInVisible(visible: Boolean) {
    visibility = if (visible) View.INVISIBLE else View.VISIBLE
}

fun View.setEnable(isEnable: Boolean, dAlpha: Float = 0.5f) {
    isEnabled = isEnable
    alpha = (isEnable then 1f) ?: dAlpha
}


/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/

/**
 * To startActivity in short manner
 * @param T is your activity
 * @param params is your put extras
 * Usage :
 *      start<YourActivity>(
"string_key" to "string_value",
"int_key" to 12
)
 * */


inline fun <reified T : Activity> Context.start(
    vararg params: Pair<String, Any?>,
    isFinish: Boolean = false,
    requestCode: Int? = null
) {
    this as Activity
    val intent = Intent(this, T::class.java).apply {
        params.forEach {
            when (val value = it.second) {
                is Int -> putExtra(it.first, value)
                is String -> putExtra(it.first, value)
                is Double -> putExtra(it.first, value)
                is Float -> putExtra(it.first, value)
                is Boolean -> putExtra(it.first, value)
                is Serializable -> putExtra(it.first, value)
                else -> throw IllegalArgumentException("Wrong param type!")
            }
            return@forEach
        }
    }
    if (requestCode != null) startActivityForResult(intent, requestCode) else startActivity(intent)
    if (isFinish) finish()
}


inline fun <reified T : Activity> Context.activityIntent(
    vararg params: Pair<String, Any?>,
    isFinish: Boolean = false,
    requestCode: Int? = null
): Intent {
    this as Activity
    val intent = Intent(this, T::class.java).apply {
        params.forEach {
            when (val value = it.second) {
                is Int -> putExtra(it.first, value)
                is String -> putExtra(it.first, value)
                is Double -> putExtra(it.first, value)
                is Float -> putExtra(it.first, value)
                is Boolean -> putExtra(it.first, value)
                is Serializable -> putExtra(it.first, value)
                else -> throw IllegalArgumentException("Wrong param type!")
            }
            return@forEach
        }
    }
    return intent
}


/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/

/**
 * To get string from Edit text directly
 * Usage :
 *      yourEditText.asString()
 * */
fun View.asString(): String {
    return when (this) {
        is AppCompatEditText -> text.toString().trim().ifNotNullOrElse({ it }, { "" })
        is AppCompatTextView -> text.toString().trim().ifNotNullOrElse({ it }, { "" })
        is AutoCompleteTextView -> text.toString().trim().ifNotNullOrElse({ it }, { "" })
        is MaterialButton -> text.toString().trim().ifNotNullOrElse({ it }, { "" })
        else -> ""
    }
}


/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/

/**
 * Alert dialog
 * Usage :
 *    showAlertDialog {
 *         setTitle("Greet")
 *         setMessage("Welcome again, want coffee?")
 *         positiveButton("Yes") {}
 *         negativeButton {}
 *    }
 * */
fun Context.showAlertDialog(dialogBuilder: AlertDialog.Builder.() -> Unit) {
    val builder = AlertDialog.Builder(this)
    builder.dialogBuilder()
    val alertDialog = builder.create()

    alertDialog.show()
    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isAllCaps = false
    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).isAllCaps = false
}

fun Context.showAlertDialogBuilder(dialog: AlertDialog.() -> Unit) {
    val builder = AlertDialog.Builder(this)
    val alertDialog = builder.create()

    alertDialog.dialog()
}

fun AlertDialog.getPositiveButton(): Button = this.getButton(AlertDialog.BUTTON_POSITIVE)

fun AlertDialog.getNegativeButton(): Button = this.getButton(AlertDialog.BUTTON_NEGATIVE)

fun AlertDialog.Builder.positiveButton(text: String, handleClick: (which: Int) -> Unit = {}) {
    this.setPositiveButton(text) { _, which -> handleClick(which) }
}

fun AlertDialog.Builder.negativeButton(text: String, handleClick: (which: Int) -> Unit = {}) {
    this.setNegativeButton(text) { _, which -> handleClick(which) }
}


fun AlertDialog.negativeButton(text: String, handleClick: (which: Int) -> Unit = {}) {
    this.setButton(AlertDialog.BUTTON_NEGATIVE, text) { _, which -> handleClick(which) }
}

fun AlertDialog.positiveButton(text: String, handleClick: (which: Int) -> Unit = {}) {
    this.setButton(AlertDialog.BUTTON_POSITIVE, text) { _, which -> handleClick(which) }
}


/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/

/**
 * Custom Dialog
 * Usage :
 *    showDialog(R.layout.dialog_confirmation) {
 *       setWidth(0.8f) // this is not compulsory it will take default 0.8f
 *    }
 *
 * */

inline fun <reified T : ViewBinding> Context.showDialog(
    layoutResourceId: Int,
    dialogBuilder: (T, d: Dialog) -> Unit = { _: ViewBinding, _: Dialog -> }
) {
    Dialog(this).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(true)
        val bin = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(context),
            layoutResourceId,
            null,
            false
        )
        setContentView((bin as T).root)
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(
                (Resources.getSystem().displayMetrics.widthPixels * 0.8f).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        dialogBuilder(bin, this)
        show()
    }
}

internal fun Dialog.setWidth(width: Float = 0.8f) {
    this.window?.setLayout(
        (Resources.getSystem().displayMetrics.widthPixels * width).toInt(),
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
}


/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/

/**
 * Inflate view in adapter
 *
 * Usage :
 *      1.) parent.inflate(R.layout.my_layout) -> default root is false
 *      2.) parent.inflate(R.layout.my_layout, true)
 * */
fun ViewGroup.inflateWithView(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

inline fun <reified T : ViewBinding> LayoutInflater.inflateWithInflater(
    @LayoutRes layoutRes: Int,
    viewGroup: ViewGroup?,
    attachToRoot: Boolean = false
): T =
    (DataBindingUtil.inflate<ViewDataBinding>(
        LayoutInflater.from(this.context),
        layoutRes,
        viewGroup,
        attachToRoot
    ) as T)

fun Context.inflateView(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(this).inflate(layoutRes, null)

inline fun <reified T : ViewBinding> ViewGroup.inflateAdapter(@LayoutRes layoutRes: Int): T {
    return (DataBindingUtil.inflate<ViewDataBinding>(
        LayoutInflater.from(this.context),
        layoutRes,
        this,
        false
    ) as T)
}

inline fun <reified T : ViewBinding> Context.inflateBindingView(@LayoutRes layoutRes: Int): T {
    return (DataBindingUtil.inflate<ViewDataBinding>(
        LayoutInflater.from(this),
        layoutRes,
        null,
        false
    ) as T)
}


/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/

/**
 * Click listener to stop multi click on view
 * */
fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}


/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/

/**
 * To save the value into preference
 * Usage :
 *
 * prefSave :
 *      prefSave("key_value_string" to "value")
 *      prefSave("key_value_integer" to 12)
 *
 * prefContain :
 *      prefContain("key")
 *
 * */



/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/

/**
 * To get Color, Resource, String from xml or res folder
 * Usage :
 *
 * getStr :
 *      getStr(R.string.your_string)
 *
 * getRes :
 *      getRes(R.drawable.your_drawable)
 *
 * getClr :
 *      getClr(R.color.your_color)
 *
 * getDimen :
 *      getDimen(R.dimen.your_dimension)
 *
 * */

fun Context.getStr(@StringRes id: Int) = resources.getString(id)

fun Context.getRes(@DrawableRes id: Int) = ResourcesCompat.getDrawable(resources, id, theme)!!

fun Context.getClr(@ColorRes id: Int) = ResourcesCompat.getColor(resources, id, theme)

fun Context.getClrStateList(@ColorRes id: Int) = ColorStateList.valueOf(getClr(id))

fun Context.getDimen(@DimenRes id: Int) = resources.getDimension(id)

/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/

/**
 * To hide keyboard
 * Usage :
 *      hideKeyboard()
 *
 * */


fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = this.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------*/

/**
 * It's like Conditional operator in JAVA
 * Usage :
 *      condition then {a} ?: {b}
 *
 *      also you can use in built kotlin function like :
 *          a.takeIf { condition } ?: b
 *
 * */


infix fun <T> Boolean.then(param: T): T? = if (this) param else null


fun String.capitalized(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase(Locale.getDefault())
        else it.toString()
    }
}


fun String.lowerCase(): String {
    return this.replaceFirstChar {
        if (it.isUpperCase())
            it.lowercase(Locale.getDefault())
        else it.toString()
    }
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
