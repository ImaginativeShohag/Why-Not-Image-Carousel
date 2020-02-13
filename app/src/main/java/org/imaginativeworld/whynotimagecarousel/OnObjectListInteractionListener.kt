package org.imaginativeworld.whynotimagecarousel

interface OnObjectListInteractionListener<T> {

    fun onClick(position: Int, dataObject: T)

    fun onLongClick(position: Int, dataObject: T)

}