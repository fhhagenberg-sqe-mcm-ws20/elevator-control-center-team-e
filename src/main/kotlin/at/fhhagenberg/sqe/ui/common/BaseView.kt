package at.fhhagenberg.sqe.ui.common

import at.fhhagenberg.sqe.viewmodel.ViewModel

abstract class BaseView<T : ViewModel>(
        protected val viewModel: T
) : ViewController {
    override fun destroy() {
        viewModel.destroy()
    }
}