package com.adrianlesniak.androidgraphqlapollo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import queries.LaunchDetailsQuery

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apolloClient = ApolloClient.builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .build()

        val launchFlow = apolloClient.query(LaunchDetailsQuery(id = "1"))
            .toFlow()

        lifecycleScope.launch {

            launchFlow.collect {
                findViewById<TextView>(R.id.site_name).text = it.data?.launch?.mission?.name
            }
        }
    }
}