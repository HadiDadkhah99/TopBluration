<a href="https://jitpack.io/#HadiDadkhah99/Navigation/"><img id="badge" src="https://jitpack.io/v/HadiDadkhah99/TopBluration.svg"></a>

# Android TopBluration
## This version is not stable !!
### Don't use it in RecyclerView in this version ( 1.0 )
<br>

## How to use it
### In Any Layout

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_height="match_parent"
    android:layout_width="match_parent">


    <com.foc.libs.focTopBluration.BlurLayout
        android:id="@+id/mainLinearLayout1"
        android:background="@drawable/i3"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        app:blur="25">


        <LinearLayout
            android:id="@+id/mainLinearLayout2"
            android:layout_centerInParent="true"
            android:layout_height="wrap_content"
            android:layout_width="match_parent"
            android:orientation="vertical">


            <androidx.cardview.widget.CardView
                android:layout_height="100dp"
                android:layout_marginBottom="8dp"
                android:layout_marginHorizontal="24dp"
                android:layout_marginTop="8dp"
                android:layout_width="match_parent"
                app:cardCornerRadius="50dp"
                app:cardElevation="24dp">

                <com.foc.libs.focTopBluration.BlurItem
                    android:layout_height="match_parent"
                    android:layout_width="match_parent" />


                <TextView
                    android:layout_gravity="center"
                    android:layout_height="wrap_content"
                    android:layout_width="wrap_content"
                    android:text="Hadi"
                    android:textColor="@color/black"
                    android:textSize="18dp"
                    android:textStyle="bold" />

            </androidx.cardview.widget.CardView>


        </LinearLayout>


    </com.foc.libs.focTopBluration.BlurLayout>


</RelativeLayout>
```
### In ScrollView (Horizental , Vertical) 

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/mainRelativeLayout2"
    android:layout_height="match_parent"
    android:layout_width="match_parent">


    <com.foc.libs.focTopBluration.BlurLayout
        android:id="@+id/mainLinearLayout1"
        android:background="@drawable/i3"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        app:blur="25">


        <ScrollView

            android:id="@+id/mainScrollView1"
            android:layout_centerInParent="true"
            android:layout_height="wrap_content"
            android:layout_width="match_parent">

            <LinearLayout
                android:id="@+id/mainLinearLayout2"
                android:layout_height="wrap_content"
                android:layout_width="match_parent"
                android:orientation="vertical">


                <androidx.cardview.widget.CardView
                    android:layout_height="100dp"
                    android:layout_marginBottom="8dp"
                    android:layout_marginHorizontal="24dp"
                    android:layout_marginTop="8dp"
                    android:layout_width="match_parent"
                    app:cardCornerRadius="50dp"
                    app:cardElevation="24dp">

                    <com.foc.libs.focTopBluration.BlurItem
                        android:layout_height="match_parent"
                        android:layout_width="match_parent" />


                    <TextView
                        android:layout_gravity="center"
                        android:layout_height="wrap_content"
                        android:layout_width="wrap_content"
                        android:text="Hadi"
                        android:textColor="@color/black"
                        android:textSize="18dp"
                        android:textStyle="bold" />

                </androidx.cardview.widget.CardView>

                <androidx.cardview.widget.CardView
                    android:layout_height="100dp"
                    android:layout_marginBottom="8dp"
                    android:layout_marginHorizontal="24dp"
                    android:layout_marginTop="8dp"
                    android:layout_width="match_parent"
                    app:cardCornerRadius="50dp"
                    app:cardElevation="24dp">

                    <com.foc.libs.focTopBluration.BlurItem
                        android:layout_height="match_parent"
                        android:layout_width="match_parent" />


                    <TextView
                        android:layout_gravity="center"
                        android:layout_height="wrap_content"
                        android:layout_width="wrap_content"
                        android:text="Hadi"
                        android:textColor="@color/black"
                        android:textSize="18dp"
                        android:textStyle="bold" />

                </androidx.cardview.widget.CardView>

            </LinearLayout>

        </ScrollView>

    </com.foc.libs.focTopBluration.BlurLayout>


</RelativeLayout>
```

