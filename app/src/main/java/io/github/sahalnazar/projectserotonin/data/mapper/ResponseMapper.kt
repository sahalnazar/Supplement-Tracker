package io.github.sahalnazar.projectserotonin.data.mapper

import io.github.sahalnazar.projectserotonin.data.db.SupplementEntity
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

    fun List<TimeWiseSupplementsToConsume?>?.toSupplementEntity() =
        this
            ?.filterNotNull()
            ?.flatMap { timeWise ->
                timeWise.supplements?.mapNotNull { supp ->
                    supp.id?.let {
                        SupplementEntity(
                            id = it,
                            name = supp.name,
                            image = supp.image,
                            isPaused = supp.isPaused,
                            servingSize = supp.servingSize,
                            lastConsumedTimestamp = supp.lastConsumedTimestamp,
                            isConsumed = supp.isConsumed,
                            snackbarTitle = supp.snackbarTitle,
                            snackbarDescription = supp.snackbarDescription,
                            timeCode = timeWise.code ?: "",
                            timeTitle = timeWise.title
                        )
                    }
                } ?: emptyList()
            } ?: emptyList()

    fun Map<String, List<SupplementEntity>>.toTimeWiseSupplementsToConsume() =
        map { (code, supplements) ->
            TimeWiseSupplementsToConsume(
                code = code,
                title = supplements.firstOrNull()?.timeTitle,
                supplements = supplements.map {
                    Supplement(
                        id = it.id,
                        name = it.name,
                        image = it.image,
                        isPaused = it.isPaused,
                        servingSize = it.servingSize,
                        lastConsumedTimestamp = it.lastConsumedTimestamp,
                        isConsumed = it.isConsumed,
                        snackbarTitle = it.snackbarTitle,
                        snackbarDescription = it.snackbarDescription
                    )
                }
            )
        }
}
