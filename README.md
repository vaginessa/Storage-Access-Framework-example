# Storage-Access-Framework-example
Example that shows how to have SD Card access on >= Lollipop devices. This is needed if You want to modify/delete files on an external SD Card

![ScreenShot](https://github.com/enricocid/Storage-Access-Framework-example/blob/master/layout-2017-06-25-150239.png)


### 1. On button click we are gonna show a rationale dialog to explain why >=Lollipop users needs to grant SD Card access (rationale dialog method). On click OK we start an intent for result to open document tree where user can pick the sdcard path

```
Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);

activity.startActivityForResult(intent, 1);
```

### 2. On activity result we save the picked sdcard path to preferences

```
saveSDCardUri(this, String.valueOf(data.getData()));

Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT)
 .show();
 ```
 
 ### 3. The method to save the sd card path to preferences:
 
 ```
 private void saveSDCardUri(Activity activity, String uri) {

        SharedPreferences preferenceSD;
        preferenceSD = activity.getSharedPreferences("sdUri", Context.MODE_PRIVATE);


        preferenceSD.edit()
                .clear()
                .apply();

        preferenceSD.edit()
                .putString("selectedSD", uri)
                .apply();
    }
```
    
4. Using getSDCardUri method You can access this string later
   
```
    private String getSDCardUri(Activity activity) {

        SharedPreferences preferenceSD = activity.getSharedPreferences("sdUri", Context.MODE_PRIVATE);

        return preferenceSD.getString("selectedSD", "");
    }
  ```
