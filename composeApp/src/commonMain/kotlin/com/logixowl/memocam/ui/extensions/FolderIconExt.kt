package com.logixowl.memocam.ui.extensions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bathroom
import androidx.compose.material.icons.rounded.Bed
import androidx.compose.material.icons.rounded.Bedtime
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BugReport
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.CatchingPokemon
import androidx.compose.material.icons.rounded.Collections
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.Diversity3
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.FamilyRestroom
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Flight
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Healing
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Kitchen
import androidx.compose.material.icons.rounded.Label
import androidx.compose.material.icons.rounded.Living
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.MonitorHeart
import androidx.compose.material.icons.rounded.Mood
import androidx.compose.material.icons.rounded.PermIdentity
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material.icons.rounded.SentimentDissatisfied
import androidx.compose.material.icons.rounded.SentimentSatisfied
import androidx.compose.material.icons.rounded.SentimentVerySatisfied
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Style
import androidx.compose.ui.graphics.vector.ImageVector
import com.logixowl.memocam.model.FolderIcon
import com.logixowl.memocam.model.FolderIcon.Folder

/**
 * Created by AP-Jake
 * on 27/06/2025
 */

fun FolderIcon.Companion.fromInt(value: Int): FolderIcon {
    return FolderIcon.entries.find { it.value == value } ?: Folder
}

val FolderIcon.Companion.asChoosableList: List<FolderIcon>
    get() = FolderIcon.entries

val FolderIcon.asImageVector: ImageVector
    get() = when (this) {
        FolderIcon.Face -> Icons.Rounded.Face
        FolderIcon.Person -> Icons.Rounded.Person
        FolderIcon.SelfImprovement -> Icons.Rounded.SelfImprovement
        FolderIcon.Mood -> Icons.Rounded.Mood
        FolderIcon.PermIdentity -> Icons.Rounded.PermIdentity

        FolderIcon.FitnessCenter -> Icons.Rounded.FitnessCenter
        FolderIcon.Healing -> Icons.Rounded.Healing
        FolderIcon.Spa -> Icons.Rounded.Spa
        FolderIcon.MonitorHeart -> Icons.Rounded.MonitorHeart
        FolderIcon.Bedtime -> Icons.Rounded.Bedtime

        FolderIcon.Pets -> Icons.Rounded.Pets
        FolderIcon.Favorite -> Icons.Rounded.Favorite
        FolderIcon.BugReport -> Icons.Rounded.BugReport
        FolderIcon.CatchingPokemon -> Icons.Rounded.CatchingPokemon

        FolderIcon.Groups -> Icons.Rounded.Groups
        FolderIcon.FamilyRestroom -> Icons.Rounded.FamilyRestroom
        FolderIcon.Home -> Icons.Rounded.Home
        FolderIcon.Diversity -> Icons.Rounded.Diversity3

        FolderIcon.CalendarToday -> Icons.Rounded.CalendarToday
        FolderIcon.PhotoCamera -> Icons.Rounded.PhotoCamera
        FolderIcon.Event -> Icons.Rounded.Event
        FolderIcon.Edit -> Icons.Rounded.Edit
        FolderIcon.Star -> Icons.Rounded.Star

        FolderIcon.Bed -> Icons.Rounded.Bed
        FolderIcon.Kitchen -> Icons.Rounded.Kitchen
        FolderIcon.Bathroom -> Icons.Rounded.Bathroom
        FolderIcon.Living -> Icons.Rounded.Living

        FolderIcon.FMap -> Icons.Rounded.Map
        FolderIcon.Flight -> Icons.Rounded.Flight
        FolderIcon.DirectionsCar -> Icons.Rounded.DirectionsCar
        FolderIcon.Place -> Icons.Rounded.Place

        FolderIcon.SentimentSatisfied -> Icons.Rounded.SentimentSatisfied
        FolderIcon.SentimentDissatisfied -> Icons.Rounded.SentimentDissatisfied
        FolderIcon.SentimentVerySatisfied -> Icons.Rounded.SentimentVerySatisfied

        FolderIcon.Bookmark -> Icons.Rounded.Bookmark
        FolderIcon.Style -> Icons.Rounded.Style
        FolderIcon.Label -> Icons.Rounded.Label
        FolderIcon.Folder -> Icons.Rounded.Folder
        FolderIcon.Collections -> Icons.Rounded.Collections
    }