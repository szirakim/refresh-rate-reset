package com.szirakiapps.refreshratereset

val actionList = listOf(
    Action(
        title = "Lowering min refresh rate",
        percentage = 0.25,
        function = {
            it.setMinRefreshRate(RefreshRate.Low)
        },
    ),
    Action(
        title = "Lowering peak refresh rate",
        percentage = 0.5,
        function = {
            it.setPeakRefreshRate(RefreshRate.Sixty)
        },
    ),
    Action(
        title = "Resetting min refresh rate",
        percentage = 0.75,
        function = {
            it.setMinRefreshRate(RefreshRate.Reset)
        },
    ),
    Action(
        title = "Resetting peak refresh rate",
        percentage = 1.0,
        function = {
            it.setPeakRefreshRate(RefreshRate.Reset)
        },
    ),
    Action(title = "Finished", percentage = 1.0, function = {}),
)