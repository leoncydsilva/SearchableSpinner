package com.leo.searchablespinner

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.leo.searchablespinner.interfaces.OnAnimationEnd
import com.leo.searchablespinner.interfaces.OnItemSelectListener
import com.leo.searchablespinner.utils.CircularReveal
import com.leo.searchablespinner.utils.SpinnerRecyclerAdapter
import kotlinx.android.synthetic.main.searchable_spinner.view.*


@Suppress("MemberVisibilityCanBePrivate", "RedundantSetter", "RedundantGetter")
class SearchableSpinner(private val context: Context) : LifecycleObserver {
    lateinit var onItemSelectListener: OnItemSelectListener
    private lateinit var dialog: AlertDialog
    //private lateinit var clickedView: View
    private lateinit var dialogView: View
    private lateinit var recyclerAdapter: SpinnerRecyclerAdapter

    var windowTopBackgroundColor: Int? = null
        @ColorInt get() = field
        set(@ColorInt colorInt) {
            field = colorInt
        }

    var windowTitleTextColor: Int = ContextCompat.getColor(context, android.R.color.white)
        @ColorInt get() = field
        set(@ColorInt colorInt) {
            field = colorInt
        }

    var negativeButtonBackgroundColor: Int? = null
        @ColorInt get() = field
        set(@ColorInt colorInt) {
            field = colorInt
        }

    var negativeButtonTextColor: Int = ContextCompat.getColor(context, android.R.color.white)
        @ColorInt get() = field
        set(@ColorInt colorInt) {
            field = colorInt
        }

    var animationDuration: Int = 420
    var showKeyboardByDefault: Boolean = true
    var spinnerCancelable: Boolean = false
    var windowTitle: String? = null
    var searchQueryHint: String = context.getString(android.R.string.search_go)
    var negativeButtonText: String = context.getString(android.R.string.cancel)
    var dismissSpinnerOnItemClick: Boolean = true
    var highlightSelectedItem: Boolean = true
    var negativeButtonVisibility: SpinnerView = SpinnerView.VISIBLE
    var windowTitleVisibility: SpinnerView = SpinnerView.GONE
    var searchViewVisibility: SpinnerView = SpinnerView.VISIBLE
    var selectedItemPosition: Int = -1
    var selectedItem: String? = null

    @Suppress("unused")
    enum class SpinnerView(val visibility: Int) {
        VISIBLE(View.VISIBLE),
        INVISIBLE(View.INVISIBLE),
        GONE(View.GONE)
    }

    init {
        initLifeCycleObserver()
    }

    fun show() {
        if (getDialogInfo(true)) {
            //clickedView = view
            dialogView = View.inflate(context, R.layout.searchable_spinner, null)
            val dialogBuilder =
                AlertDialog.Builder(context)
                    .setView(dialogView)
                    .setCancelable(spinnerCancelable || negativeButtonVisibility != SpinnerView.VISIBLE)

            dialog = dialogBuilder.create()
            dialog.initView()
            initDialogColorScheme()
            dialog.show()
            dialog.initAlertDialogWindow()
        }
    }

    fun dismiss() {
        if (getDialogInfo(false))
            CircularReveal.startReveal(false, dialog, object : OnAnimationEnd {
                override fun onAnimationEndListener(isRevealed: Boolean) {
                    toggleKeyBoard(false)
                    if (::recyclerAdapter.isInitialized) recyclerAdapter.filter(null)
                }
            }, animationDuration)
    }

    fun setSpinnerListItems(spinnerList: ArrayList<String>) {
        recyclerAdapter =
            SpinnerRecyclerAdapter(context, spinnerList, object : OnItemSelectListener {
                override fun setOnItemSelectListener(position: Int, selectedString: String) {
                    selectedItemPosition = position
                    selectedItem = selectedString
                    if (dismissSpinnerOnItemClick) dismiss()
                    if (::onItemSelectListener.isInitialized) onItemSelectListener.setOnItemSelectListener(
                        position,
                        selectedString
                    )
                }
            })
    }

    //Private helper methods
    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun dismissDialogOnDestroy() {
        if (getDialogInfo(false))
            dialog.dismiss()
    }

    private fun initLifeCycleObserver() {
        if (context is AppCompatActivity)
            context.lifecycle.addObserver(this)
        if (context is FragmentActivity)
            context.lifecycle.addObserver(this)
        if (context is Fragment)
            context.lifecycle.addObserver(this)
    }

    private fun getDialogInfo(toShow: Boolean): Boolean {
        return if (toShow) {
            !::dialog.isInitialized || !dialog.isShowing
        } else
            ::dialog.isInitialized && dialog.isShowing && dialog.window != null && dialog.window?.decorView != null && dialog.window?.decorView?.isAttachedToWindow!!
    }

    private fun AlertDialog.initView() {
        setOnShowListener {
            CircularReveal.startReveal(true, this, object : OnAnimationEnd {
                override fun onAnimationEndListener(isRevealed: Boolean) {
                    if (isRevealed) {
                        toggleKeyBoard(true)
                    }
                }
            }, animationDuration)
        }

        if (spinnerCancelable || negativeButtonVisibility != SpinnerView.VISIBLE)
            setOnCancelListener {
                if (::recyclerAdapter.isInitialized) recyclerAdapter.filter(
                    null
                )
            }

        dialog.setOnKeyListener { _, keyCode, event ->
            if (event?.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                this@SearchableSpinner.dismiss()
            }
            false
        }

        //init WindowTittle
        if (windowTitle != null || windowTitleVisibility.visibility == SearchView.VISIBLE) {
            dialogView.textViewTitle.visibility = View.VISIBLE
            dialogView.textViewTitle.text = windowTitle
            dialogView.textViewTitle.setTextColor(windowTitleTextColor)
        } else dialogView.textViewTitle.visibility = windowTitleVisibility.visibility

        //init SearchView
        if (searchViewVisibility.visibility == SearchView.VISIBLE) {
            dialogView.searchView.queryHint = searchQueryHint
            dialogView.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (::recyclerAdapter.isInitialized) recyclerAdapter.filter(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (::recyclerAdapter.isInitialized) recyclerAdapter.filter(newText)
                    return false
                }

            })
        } else dialogView.searchView.visibility = searchViewVisibility.visibility


        //init NegativeButton
        if (negativeButtonVisibility.visibility == SearchView.VISIBLE) {
            dialogView.buttonClose.setOnClickListener {
                it.isClickable = false
                this@SearchableSpinner.dismiss()
            }
            dialogView.buttonClose.text = negativeButtonText
            dialogView.buttonClose.setTextColor(negativeButtonTextColor)
        } else dialogView.buttonClose.visibility = negativeButtonVisibility.visibility

        //set Recycler Adapter
        if (::recyclerAdapter.isInitialized) {
            recyclerAdapter.highlightSelectedItem = highlightSelectedItem
            dialogView.recyclerView.adapter = recyclerAdapter
        }
    }

    private fun initDialogColorScheme() {
        if (windowTopBackgroundColor != null)
            dialogView.headLayout.background = ColorDrawable(windowTopBackgroundColor!!)
        if (negativeButtonBackgroundColor != null)
            dialogView.buttonClose.backgroundTintList =
                ColorStateList.valueOf(negativeButtonBackgroundColor!!)
    }

    private fun AlertDialog.initAlertDialogWindow() {
        val colorDrawable = ColorDrawable(Color.TRANSPARENT)
        val insetBackgroundDrawable = InsetDrawable(colorDrawable, 50, 40, 50, 40)
        window?.setBackgroundDrawable(insetBackgroundDrawable)
        window?.attributes?.layoutAnimationParameters
        window?.attributes?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun toggleKeyBoard(showKeyBoard: Boolean) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (showKeyboardByDefault && showKeyBoard) {
            dialogView.searchView.post {
                dialogView.searchView.requestFocus()
                imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1)
            }
        } else {
            imm?.toggleSoftInput(0, 0)
        }
    }
}