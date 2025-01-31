package dev.akash42.composepoc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun rememberPassbookRecentStateGenerator(
    numberOfEntries: Int = 3
): PassbookRecentStateGenerator {
    return remember {
        println("whygg remember called")
        PassbookRecentStateGenerator(
            numberOfEntries = numberOfEntries
        )
    }
}

data class PassbookRecentStateGenerator(
    val numberOfEntries: Int,
) {
    init {
        println("whygg PassbookRecentStateGenerator init called")
    }
    var passbookEntriesState by mutableStateOf(RecentPassbookEntriesState(listOf(), false, null))
        private set
    fun refresh(){
        println("whygg refresh called")
        val existingEntries = passbookEntriesState.list.toMutableList()
        println("whygg original list $existingEntries")
        existingEntries.add(0, PassBookEntry("Name", Random.nextInt(500, 5001).toString()))
        val newList = existingEntries.take(numberOfEntries).toList()
        println("whygg newlist from passbook: $newList")
        passbookEntriesState = passbookEntriesState.copy(list = newList)
    }
}

data class RecentPassbookEntriesState(
    val list: List<PassBookEntry>,
    val isLoading: Boolean,
    val error: String?
)
data class PassBookEntry(
    val name: String,
    val amount: String
)

@Composable
fun PassbookRecentEntriesComposable(
    txnUid: String?
) {
    val stateGenerator = rememberPassbookRecentStateGenerator()
    val state by remember(txnUid) {
        derivedStateOf {
            stateGenerator.passbookEntriesState
        }
    }
    LaunchedEffect(txnUid) {
        if(txnUid != null) {
            println("whygg in LaunchedEffect $txnUid")
            stateGenerator.refresh()
        }
    }
    PassbookRecentEntries(
        state = state,
    )
}

@Composable
fun PassbookRecentEntries(
    state: RecentPassbookEntriesState,
    modifier: Modifier = Modifier
) {
    println("whygg state: $state")
    if(state.isLoading) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Loading...",
                modifier = Modifier.height(50.dp)
            )
        }
    } else {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = "Recent Transactions",
                modifier = Modifier.fillMaxWidth()
            )
            state.list.forEach {
                Text(
                    text = "${it.name} - ${it.amount}",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}