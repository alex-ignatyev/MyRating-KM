package screens.main.tobacco.tobacco_info.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue

@Composable
fun Rating(value: Int, modifier: Modifier = Modifier, changeRating: (Int) -> Unit) {
    val valuedIndex = value - 1

    AnimatedInfiniteList(
        modifier = modifier.fillMaxWidth().wrapContentHeight().padding(vertical = 32.dp),
        items = listOf(-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
        initialFirstVisibleIndex = if (valuedIndex == -1) 0 else valuedIndex,
        activeItemSize = 48.dp,
        inactiveItemSize = 24.dp,
        itemContent = { animationProgress, index, item, size ->
            val color = animationProgress.color
            val scale = animationProgress.scale

            changeRating(animationProgress.itemIndex - 1)

            if (index in 2..11) {
                Box(
                    modifier = Modifier
                        .scale(scale)
                        .background(color, CircleShape)
                        .size(size),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "$item",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .scale(scale)
                        .size(size),
                    contentAlignment = Alignment.Center
                ) {

                }
            }
        }
    )
}

/**
 *  Infinite list with color and scale animation for selecting aspect ratio
 *
 * @param items the data list
 * @param initialFirstVisibleIndex first visible item of the list. This can be
 * negative number too. It's in range of [items]. If there are 10 items -2th item is
 * actually 8th item is the first visible item on screen
 * @param visibleItemCount count of items that are visible at any time
 * @param activeItemSize width or height of selected item
 * @param inactiveItemSize width or height of unselected items
 * @param spaceBetweenItems padding between 2 items
 * @param selectorIndex index of selector. When [itemScaleRange] is odd number it's center of
 * selected item, when [itemScaleRange] is even number it's center of item with selector index
 * and the one next to it
 * @param itemScaleRange range of area of scaling. When this value is odd
 * any item that enters half of item size range subject to  being scaled. When this value is even
 * any item in 2 item size range is subject to being scaled
 * @param showPartialItem show items partially that are at the start and end
 * @param key a factory of stable and unique keys representing the item. Using the same key
 * for multiple items in the list is not allowed. Type of the key should be saveable
 * via Bundle on Android. If null is passed the position in the list will represent the key.
 * When you specify the key the scroll position will be maintained based on the key, which
 * means if you add/remove items before the current visible item the item with the given key
 * will be kept as the first visible one.
 * @param contentType a factory of the content types for the item. The item compositions of
 * the same type could be reused more efficiently. Note that null is a valid type and items of such
 * type will be considered compatible.
 * @param itemContent the content displayed by a single item
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun <T> AnimatedInfiniteList(
    modifier: Modifier = Modifier,
    items: List<T>,
    initialFirstVisibleIndex: Int,
    visibleItemCount: Int = 5,
    activeItemSize: Dp,
    inactiveItemSize: Dp,
    spaceBetweenItems: Dp = 4.dp,
    selectorIndex: Int = -1,
    itemScaleRange: Int = 1,
    showPartialItem: Boolean = false,
    orientation: Orientation = Orientation.Horizontal,
    key: ((index: Int) -> Any)? = null,
    contentType: (index: Int) -> Any? = { null },
    itemContent: @Composable LazyItemScope.(
        animationProgress: AnimationProgress, index: Int, item: T, size: Dp
    ) -> Unit
) {
    val inactiveItemScale = inactiveItemSize.value / activeItemSize.value

    val listDimension = activeItemSize * visibleItemCount + spaceBetweenItems * (visibleItemCount - 1)

    val listModifier = if (orientation == Orientation.Horizontal) {
        Modifier.width(listDimension)
    } else {
        Modifier.height(listDimension)
    }

    val availableSpace = LocalDensity.current.run { listDimension.toPx() }

    val itemSize = LocalDensity.current.run { activeItemSize.toPx() }

    // number of items
    val totalItemCount = items.size

    // Number of items that are visible
    val oddNumberOfVisibleItems = visibleItemCount % 2 == 1

    // Index of selector(item that is selected)  in circular list
    val indexOfSelector = (if (selectorIndex == -1) {
        if (oddNumberOfVisibleItems && !showPartialItem) {
            visibleItemCount / 2
        } else {
            visibleItemCount / 2 - 1
        }
    } else selectorIndex).coerceIn(0, visibleItemCount - 1)


    val centerItem = (itemSize / 2).toInt()
    val initialVisibleGlobalIndex = centerItem - centerItem % totalItemCount + initialFirstVisibleIndex

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = initialFirstVisibleIndex,
        initialFirstVisibleItemScrollOffset = if (showPartialItem) (itemSize / 2).toInt() else 0
    )

    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AnimatedCircularListImpl(
            modifier = listModifier,
            items = items,
            lazyListState = listState,
            flingBehavior = flingBehavior,
            initialFirstVisibleIndex = initialVisibleGlobalIndex,
            visibleItemCount = visibleItemCount,
            availableSpace = availableSpace,
            itemSize = activeItemSize,
            spaceBetweenItems = spaceBetweenItems,
            totalItemCount = totalItemCount,
            indexOfSelector = indexOfSelector,
            itemScaleRange = itemScaleRange,
            showPartialItem = showPartialItem,
            inactiveItemFraction = inactiveItemScale,
            key = key,
            contentType = contentType,
            itemContent = itemContent
        )
    }
}

@Composable
private fun <T> AnimatedCircularListImpl(
    modifier: Modifier,
    items: List<T>,
    lazyListState: LazyListState,
    flingBehavior: FlingBehavior,
    initialFirstVisibleIndex: Int,
    visibleItemCount: Int,
    availableSpace: Float,
    itemSize: Dp,
    spaceBetweenItems: Dp,
    totalItemCount: Int,
    indexOfSelector: Int,
    itemScaleRange: Int,
    showPartialItem: Boolean,
    inactiveItemFraction: Float,
    key: ((index: Int) -> Any)?,
    contentType: (index: Int) -> Any?,
    itemContent: @Composable LazyItemScope.(
        animationProgress: AnimationProgress, index: Int, item: T, size: Dp
    ) -> Unit
) {

    val density = LocalDensity.current
    val spaceBetweenItemsPx = density.run { spaceBetweenItems.toPx() }

    val content: LazyListScope.() -> Unit = {
        items(
            count = items.size, key = key, contentType = contentType
        ) { globalIndex ->
            AnimatedItems(
                lazyListState = lazyListState,
                initialFirstVisibleIndex = initialFirstVisibleIndex,
                indexOfSelector = indexOfSelector,
                itemScaleRange = itemScaleRange,
                showPartialItem = showPartialItem,
                globalIndex = globalIndex,
                availableSpace = availableSpace,
                itemSize = itemSize,
                spaceBetweenItems = spaceBetweenItemsPx,
                visibleItemCount = visibleItemCount,
                totalItemCount = totalItemCount,
                inactiveItemScale = inactiveItemFraction
            ) { animationProgress: AnimationProgress, size: Dp ->
                val localIndex = globalIndex % totalItemCount
                itemContent(animationProgress, localIndex, items[localIndex], size)
            }
        }
    }
    LazyRow(
        modifier = modifier,
        state = lazyListState,
        horizontalArrangement = Arrangement.spacedBy(spaceBetweenItems),
        flingBehavior = flingBehavior
    ) {
        content()
    }
}

@Composable
private fun LazyItemScope.AnimatedItems(
    lazyListState: LazyListState,
    initialFirstVisibleIndex: Int,
    indexOfSelector: Int,
    itemScaleRange: Int,
    showPartialItem: Boolean,
    globalIndex: Int,
    availableSpace: Float,
    itemSize: Dp,
    spaceBetweenItems: Float,
    visibleItemCount: Int,
    totalItemCount: Int,
    inactiveItemScale: Float,
    itemContent: @Composable LazyItemScope.(animationProgress: AnimationProgress, size: Dp) -> Unit

) {
    var selectedIndex by remember {
        mutableStateOf(-1)
    }

    val itemSizePx = LocalDensity.current.run { itemSize.toPx() }

    val animationData by remember(
        indexOfSelector,
        visibleItemCount,
        inactiveItemScale,
        itemScaleRange,
        showPartialItem
    ) {
        derivedStateOf {
            val animationData = getAnimationProgress(
                lazyListState = lazyListState,
                initialFirstVisibleIndex = initialFirstVisibleIndex,
                indexOfSelector = indexOfSelector,
                itemScaleRange = itemScaleRange,
                showPartialItem = showPartialItem,
                globalIndex = globalIndex,
                selectedIndex = selectedIndex,
                availableSpace = availableSpace,
                itemSize = itemSizePx,
                spaceBetweenItems = spaceBetweenItems,
                visibleItemCount = visibleItemCount,
                totalItemCount = totalItemCount,
                inactiveScale = inactiveItemScale
            )

            selectedIndex = animationData.globalItemIndex
            animationData
        }
    }
    itemContent(animationData, itemSize)
}

/**
 * get color, scale and selected index for scroll progress for infinite or circular list with
 * [Int.MAX_VALUE] global index count
 *
 * @param lazyListState A state object that can be hoisted to control and observe scrolling
 * @param initialFirstVisibleIndex index of item that is at the beginning of the list initially
 * @param indexOfSelector global index of element of selector of infinite items. Item with
 * this index is selected item
 * @param globalIndex index of current item. This index changes for every item in list
 * that calls this function
 * @param selectedIndex global index of currently selected item. This index changes only
 * when selected item is changed due to scroll
 * @param availableSpace space that is reserved for items and space between items. This
 * param is list width/height minus padding values in respective axis.
 * @param itemSize width/height of each item
 * @param spaceBetweenItems space between each item
 * @param visibleItemCount count of visible items on screen
 * @param totalItemCount count of items that are displayed in circular list
 * @param inactiveScale lower scale that items can be scaled to. It should be less than 1f
 */
private fun getAnimationProgress(
    lazyListState: LazyListState,
    initialFirstVisibleIndex: Int,
    indexOfSelector: Int,
    itemScaleRange: Int,
    showPartialItem: Boolean,
    globalIndex: Int,
    selectedIndex: Int,
    availableSpace: Float,
    itemSize: Float,
    spaceBetweenItems: Float,
    visibleItemCount: Int,
    totalItemCount: Int,
    inactiveScale: Float
): AnimationProgress {

    val visibleItems = lazyListState.layoutInfo.visibleItemsInfo
    val currentItem: LazyListItemInfo? = visibleItems.firstOrNull { it.index == globalIndex }
    val halfItemSize = itemSize / 2

    var isRangeOfSelectionOdd = itemScaleRange % 2 == 1
    // If partial item is shown range
    if (showPartialItem) isRangeOfSelectionOdd = !isRangeOfSelectionOdd

    // Position of selector item
    val selectorPosition =
        getSelectorPosition(isRangeOfSelectionOdd, indexOfSelector, itemSize, spaceBetweenItems)

    // Get offset of each item relative to start of list x=0 or y=0 position
    val itemCenter = (halfItemSize).toInt() + if (currentItem != null) {
        currentItem.offset
    } else {
        // Convert global indexes to indexes in range of 0..visibleItemCount
        // when current item is null on initial run
        val localIndex =
            (visibleItemCount + globalIndex - initialFirstVisibleIndex) % visibleItemCount
        var initialItemCenter = (localIndex * itemSize + localIndex * spaceBetweenItems)
        // If we show partial items offset from 0 by half of items size to right or up
        if (showPartialItem) initialItemCenter -= halfItemSize
        initialItemCenter.toInt()
    }

    // get scale of current item based on distance between items center to selector
    val scale = getScale(
        rangeOfSelection = itemScaleRange,
        selectorPosition = selectorPosition,
        itemCenter = itemCenter,
        inactiveScale = inactiveScale,
        itemSize = itemSize,
        spaceBetweenItems = spaceBetweenItems
    ).coerceIn(0f, 1f)

    val globalSelectedIndex =
        getIndexClosestToSelector(selectedIndex, halfItemSize, selectorPosition, visibleItems)


    // Index of item in list. If list has 7 items initial item index is 3
    // When selector changes we get what it(in infinite list) corresponds to in item list
    val itemIndex = if (globalSelectedIndex > 0) {
        globalSelectedIndex % totalItemCount
    } else {
        indexOfSelector
    }

    // This is the fraction between lower bound and 1f. If lower bound is .9f we have
    // range of 0.9f..1f for scale calculation
    val scalingInterval = 1f - inactiveScale

    // Scale for color when scale is at lower bound color scale is zero
    // when scale reaches upper bound(1f) color scale is 1f which is target color
    // when argEvaluator evaluates color
    val colorScale = if (scalingInterval == 0f) 1f else ((scale - inactiveScale) / scalingInterval).coerceIn(0f, 1f)

    val activeColor = if (itemIndex <= 4) Color.Red else if (itemIndex in 5..7) Color.Yellow else Color.Green

    // Interpolate color between start and end color based on color scale
    val color = lerp(Color.LightGray, activeColor, colorScale)

    return AnimationProgress(
        scale = scale,
        color = color,
        itemOffset = itemCenter,
        itemFraction = itemCenter / availableSpace,
        globalItemIndex = globalSelectedIndex,
        itemIndex = itemIndex
    )
}

/**
 * Class that contains animated list animation properties
 * @param scale scale of the current item
 * @param color of the current item
 * @param itemOffset offset in px of current item from start of list
 * @param itemFraction offset in percent of current item from start of list relative to
 * width or height minus padding values in this axis
 * @param globalItemIndex index of current item. If it's returned from infinite list
 * it's real value of this index
 * @param itemIndex index of current item in range of total item count. It can be in range
 * of 0..item count -1
 */
@Immutable
data class AnimationProgress(
    val scale: Float,
    val color: Color,
    val itemOffset: Int,
    val itemFraction: Float,
    val globalItemIndex: Int,
    val itemIndex: Int
)

/**
 * Get index of item that is closest to selector position
 */
private fun getIndexClosestToSelector(
    selectedIndex: Int,
    halfItemSize: Float,
    selectorPosition: Float,
    visibleItems: List<LazyListItemInfo>
): Int {
    var distance = Int.MAX_VALUE

    var globalSelectedIndex = selectedIndex

    visibleItems.forEach {
        val itemDistanceToSelector = ((it.offset + halfItemSize) - selectorPosition).absoluteValue
        if (itemDistanceToSelector < distance) {
            distance = itemDistanceToSelector.toInt()
            globalSelectedIndex = it.index
        }
    }

    return globalSelectedIndex
}

/**
 *  Get position of selector item
 *  ```
 *  3 item range of selection <> = item, -- space between items
 *     SELECTOR
 * --<>--<|>--<>
 * 4 item range of selection
 *       SELECTOR
 * --<>--<>-|-<>--<>--
 *  ```
 */
private fun getSelectorPosition(
    isRangeOfSelectionOdd: Boolean,
    indexOfSelector: Int,
    itemSize: Float,
    spaceBetweenItems: Float
): Float {
    val halfItemSize = itemSize / 2

    return if (isRangeOfSelectionOdd) {
        // Scale range is odd so selector position is the center of item
        //  with indexOfSelector index
        (indexOfSelector) * (itemSize + spaceBetweenItems) + halfItemSize
    } else {
        // Scale range is even so selector position is space between indexOfSelector one
        // and next one
        (indexOfSelector) * (itemSize + spaceBetweenItems) + itemSize + spaceBetweenItems / 2
    }
}

/**
 * get scale based on whether it's initial run of list,
 * [LazyListState]'S [LazyListLayoutInfo.visibleItemsInfo]  list is empty,
 * or current scroll state of list.
 *
 * @param inactiveScale lower scale that items can be scaled to. It should be less than 1f
 * @param itemSize width/height of each item
 * @param spaceBetweenItems space between each item
 * @param selectorPosition position of selector or selected item
 * @param itemCenter offset of item from start of the list's x or y zero position
 */
private fun getScale(
    rangeOfSelection: Int,
    selectorPosition: Float,
    itemCenter: Int,
    inactiveScale: Float,
    itemSize: Float,
    spaceBetweenItems: Float,
): Float {

    // Check how far this item is to selector index.
    val distanceToSelector = (selectorPosition - itemCenter).absoluteValue
    // When offset of an item is in this region it gets scaled.
    // region size is calculated as
    // half space + (item width or height) + half space
    val scaleRegionSize = (itemSize + spaceBetweenItems) * (rangeOfSelection + 1) / 2

    return calculateScale(
        distanceToSelector,
        scaleRegionSize,
        inactiveScale
    )
}

/**
 * Calculate scale that is inside scale region based on [minimum]..1f range
 */
private fun calculateScale(
    distanceToSelector: Float,
    scaleRegionSize: Float,
    minimum: Float
): Float {
    return if (distanceToSelector < scaleRegionSize) {

        // item is in scale region. Check where exactly it is in this region
        val fraction = (scaleRegionSize - distanceToSelector) / scaleRegionSize

        // scale fraction between start and 1f.
        // If start is .9f and fraction is 50% our scale is .9f + .1f*50/100 = .95f
        lerp(start = minimum, stop = 1f, fraction = fraction)
    } else {
        minimum

    }.coerceIn(minimum, 1f)
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}
