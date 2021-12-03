package kz.garage.lifecycle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified T: ViewModel> ViewModelProvider.Factory.createViewModel(
    owner: ViewModelStoreOwner
): T = ViewModelProvider(owner, this).get(T::class.java)


inline fun <reified T: ViewModel> ViewModelProvider.Factory.createViewModel(
    store: ViewModelStore
): T = ViewModelProvider(store, this).get(T::class.java)
