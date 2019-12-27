package co.anitrend.multisearch.contract

import android.animation.Animator

internal abstract class SimpleAnimationListener : Animator.AnimatorListener {

    /**
     * Notifies the repetition of the animation.
     *
     * @param animation The animation which was repeated.
     */
    override fun onAnimationRepeat(animation: Animator?) {

    }

    /**
     * Notifies the cancellation of the animation. This callback is not invoked
     * for animations with repeat count set to INFINITE.
     *
     * @param animation The animation which was canceled.
     */
    override fun onAnimationCancel(animation: Animator?) {

    }

    /**
     * Notifies the start of the animation.
     *
     * @param animation The started animation.
     */
    override fun onAnimationStart(animation: Animator?) {

    }
}