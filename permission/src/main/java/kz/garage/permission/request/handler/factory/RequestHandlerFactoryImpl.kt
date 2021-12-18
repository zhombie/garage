package kz.garage.permission.request.handler.factory

import androidx.fragment.app.FragmentManager
import kz.garage.permission.request.handler.RequestHandler
import kz.garage.permission.request.handler.RequestHandlerFragment

class RequestHandlerFactoryImpl constructor(
    private val fragmentManager: FragmentManager
) : RequestHandlerFactory {

    companion object {
        private const val FRAGMENT_TAG = "kz.garage.permission.fragment"
    }

    override fun provideHandler(): RequestHandler {
        var fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG) as? RequestHandler
        if (fragment == null) {
            fragment = RequestHandlerFragment()

            fragmentManager.beginTransaction()
                .add(fragment, FRAGMENT_TAG)
                .commitAllowingStateLoss()
        }
        return fragment
    }

}