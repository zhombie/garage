package kz.garage.permission.request.status

fun PermissionStatus.isGranted(): Boolean =
    this is PermissionStatus.Granted

fun PermissionStatus.isDenied(): Boolean =
    this is PermissionStatus.Denied

fun PermissionStatus.isPermanentlyDenied(): Boolean =
    this is PermissionStatus.Denied.Permanently

fun PermissionStatus.shouldShowRationale(): Boolean =
    this is PermissionStatus.Denied.ShouldShowRationale

fun PermissionStatus.isRequestRequired(): Boolean =
    this is PermissionStatus.RequestRequired

fun List<PermissionStatus>.isAllGranted(): Boolean =
    all { it.isGranted() }

fun List<PermissionStatus>.isAllDenied(): Boolean =
    all { it.isDenied() }

fun List<PermissionStatus>.isAllShouldShowRationale(): Boolean =
    all { it.isDenied() && it.shouldShowRationale() }

fun List<PermissionStatus>.isAllPermanentlyDenied(): Boolean =
    all { it.isDenied() && it.isPermanentlyDenied() }

fun List<PermissionStatus>.isAllRequestRequired(): Boolean =
    all { it.isRequestRequired() }

fun List<PermissionStatus>.isAnyGranted(): Boolean =
    any { it.isGranted() }

fun List<PermissionStatus>.isAnyDenied(): Boolean =
    any { it.isDenied() }

fun List<PermissionStatus>.isAnyShouldShowRationale(): Boolean =
    any { it.isDenied() && it.shouldShowRationale() }

fun List<PermissionStatus>.isAnyPermanentlyDenied(): Boolean =
    any { it.isDenied() && it.isPermanentlyDenied() }

fun List<PermissionStatus>.isAnyRequestRequired(): Boolean =
    any { it.isRequestRequired() }
