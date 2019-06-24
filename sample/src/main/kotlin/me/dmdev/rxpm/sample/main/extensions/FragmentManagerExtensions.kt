@file:Suppress("NOTHING_TO_INLINE")

package me.dmdev.rxpm.sample.main.extensions

import androidx.fragment.app.*
import me.dmdev.rxpm.sample.*


inline fun FragmentManager.openScreen(
    fragment: Fragment,
    tag: String = fragment.javaClass.name,
    addToBackStack: Boolean = true
) {
    beginTransaction()
        .replace(R.id.container, fragment, tag)
        .also { if (addToBackStack) it.addToBackStack(null) }
        .commit()
}

inline val FragmentManager.currentScreen: Fragment?
    get() = this.findFragmentById(R.id.container)

inline fun FragmentManager.back() {
    popBackStackImmediate()
}

inline fun <reified T> FragmentManager.findScreen(): T? {
    return findFragmentByTag(T::class.java.name) as? T
}

inline fun FragmentManager.clearBackStack() {
    for (i in 0..backStackEntryCount) {
        popBackStackImmediate()
    }
}

inline fun FragmentManager.showDialog(
    dialog: DialogFragment,
    tag: String = dialog.javaClass.name
) {
    executePendingTransactions()
    findScreen<DialogFragment>()?.dismiss()
    dialog.show(this, tag)
    executePendingTransactions()
}
