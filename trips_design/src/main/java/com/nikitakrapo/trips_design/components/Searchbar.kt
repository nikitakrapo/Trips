package com.nikitakrapo.trips_design.components

//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun SearchBar(
//    modifier: Modifier = Modifier,
//    onSearchValueChanged: (String) -> Unit = {},
//    onDone: (String) -> Unit = {}
//) {
//    var input by remember { mutableStateOf("") }
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    OutlinedTextField(
//        modifier = modifier,
//        value = input,
//        onValueChange = {
//            onSearchValueChanged(it)
//            input = it
//        },
//        shape = RoundedCornerShape(50),
//        leadingIcon = {
//            Icon(
//                imageVector = Icons.Filled.Search,
//                contentDescription = stringResource(R.string.cd_search)
//            )
//        },
//        keyboardActions = KeyboardActions(
//            onDone = {
//                onDone(input)
//                keyboardController?.hide()
//            }
//        ),
//        maxLines = 1,
//        singleLine = true
//    )
//}