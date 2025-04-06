package io.github.sahalnazar.projectserotonin.data.mapper

import io.github.sahalnazar.projectserotonin.data.model.local.Supplement
import io.github.sahalnazar.projectserotonin.data.model.local.TimeWiseSupplementsToConsume
import io.github.sahalnazar.projectserotonin.data.model.remote.MainResponse

object ResponseMapper {
    fun List<MainResponse.Data.ItemsToConsume?>.toTimeWiseSupplementsToConsume(): List<TimeWiseSupplementsToConsume?> {
        return this.map {
            TimeWiseSupplementsToConsume(
                code = it?.code,
                title = it?.title,
                supplements = it?.items?.map { supplement ->
                    Supplement(
                        id = supplement?.product?.id,
                        name = supplement?.product?.name,
                        image = supplement?.product?.image,
                        isPaused = supplement?.product?.regimen?.status == "PAUSED",
                        servingSize = supplement?.product?.regimen?.servingSize,
                        lastConsumedTimestamp = supplement?.latestConsumption?.timestamp,
                        isConsumed = supplement?.product?.consumed ?: false,
                        snackbarTitle = supplement?.product?.regimen?.name,
                        snackbarDescription = supplement?.product?.regimen?.description
                    )
                }
            )
        }
    }
}
