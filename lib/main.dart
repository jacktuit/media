

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:permission_handler/permission_handler.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(

        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home:  MyPage(),
    );
  }
}


class MyPage extends StatefulWidget {
  @override
  State<MyPage> createState() => _MyPageState();
}




class _MyPageState extends State<MyPage> {
  final MethodChannel platform = MethodChannel('com.kollus.media/meth');


  @override
  void initState() {
    // TODO: implement initState
    requestPermission();
    super.initState();
  }
  Future<void> requestPermission() async {
    // Request the permission
    print('Camera permission granted');

    PermissionStatus status = await Permission.storage.request();

    if (status.isGranted) {
      // Permission granted, perform necessary tasks
      print('Camera permission granted');
    } else if (status.isDenied) {
      // Permission denied
      print('Camera permission denied');
    } else if (status.isPermanentlyDenied) {
      // Permission permanently denied
      print('Camera permission permanently denied');
    }
  }


  String message='';

  Future<void> getDataFromNative() async {
    try {
      final result = await platform.invokeMethod('getData');
      message=result??'null';
      // Process the result here
      print(result);
    } on PlatformException catch (e) {
      // Handle exception
      print('Failed to get data from native: ${e.message}');
    }
    setState(() {

    });
  }
  Future<void> tet() async {
    try {
      final result = await platform.invokeMethod('sub');
      message=result;
      // Process the result here
      print(result);
    } on PlatformException catch (e) {
      // Handle exception
      print('Failed to get data from native: ${e.message}');
    }
    setState(() {

    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('My Page'),
      ),
      body: Column(
        children: [
          Center(
            child: ElevatedButton(
              onPressed: getDataFromNative, // Call method channel on button press
              child: Text('Get Data'),
            ),
          ),   Center(
            child: ElevatedButton(
              onPressed: tet, // Call method channel on button press
              child: Text('sdcfsdcsdc Data'),
            ),
          ),
          Text(message)
        ],
      ),
    );
  }
}

