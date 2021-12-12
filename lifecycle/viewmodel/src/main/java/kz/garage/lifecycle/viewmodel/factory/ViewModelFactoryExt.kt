package kz.garage.lifecycle.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified T: ViewModel> ViewModelProvider.Factory.createViewModel(
    owner: ViewModelStoreOwner
): T = ViewModelProvider(owner, this)[T::class.java]


inline fun <reified T: ViewModel> ViewModelProvider.Factory.createViewModel(
    store: ViewModelStore
): T = ViewModelProvider(store, this)[T::class.java]
