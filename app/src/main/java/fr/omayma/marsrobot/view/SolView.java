package fr.omayma.marsrobot.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SolView extends View {

    private static final int NUM_TRIANGLES = 16;
    private float[] values; // Array of 16 values representing wind values

    private Paint outerCirclePaint;
    private Paint trianglePaint;

    public SolView(Context context) {
        super(context);
        init();
    }

    public SolView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SolView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.BLACK);
        outerCirclePaint.setStyle(Paint.Style.STROKE);

        trianglePaint = new Paint();
        trianglePaint.setColor(Color.BLUE);
        trianglePaint.setStyle(Paint.Style.FILL);

        values = new float[NUM_TRIANGLES];
        for (int i = 0; i < NUM_TRIANGLES; i++) {
            values[i] = (float) Math.random() * 100;
        }
    }

    public void setValues(float[] values) {
        if (values.length == NUM_TRIANGLES) {
            this.values = values;
            invalidate();
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Center of the canvas
        int centerX = width / 2;
        int centerY = height / 2;

        // Maximum value for scaling the triangle height
        float maxValue = getMaxValueFromArray(values);

        // Radius of the outer circle
        float radius = width / 2;

        // Draw the gray background for the circle
        Paint circleBackgroundPaint = new Paint();
        circleBackgroundPaint.setColor(Color.LTGRAY); // Light gray for the background
        circleBackgroundPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius, circleBackgroundPaint);

        // Draw the outer circle outline
        canvas.drawCircle(centerX, centerY, radius, outerCirclePaint);

        // Draw cross lines (North-South and East-West)
        Paint crossLinePaint = new Paint();
        crossLinePaint.setColor(Color.GRAY); // Gray for the lines
        crossLinePaint.setStrokeWidth(2); // Adjust line thickness as needed

        // North-South line
        canvas.drawLine(centerX, centerY - radius, centerX, centerY + radius, crossLinePaint);

        // East-West line
        canvas.drawLine(centerX - radius, centerY, centerX + radius, centerY, crossLinePaint);

        // Angle between each triangle (360° / 16)
        float angleStep = 360f / NUM_TRIANGLES;

        // Loop to draw each triangle
        for (int i = 0; i < NUM_TRIANGLES; i++) {
            // Calculate the angle for the current triangle
            float angle = i * angleStep;

            // Convert angle to radians for use with sin and cos
            float angleInRadians = (float) Math.toRadians(angle);

            // Calculate the height of the triangle based on its value
            float triangleHeight = (values[i] / maxValue) * radius;

            // Apex point of the triangle, adjusted by wind speed
            float apexX = (float) (centerX + (radius - triangleHeight) * Math.sin(angleInRadians));
            float apexY = (float) (centerY - (radius - triangleHeight) * Math.cos(angleInRadians));

            // Base points of the triangle (these will be constant, just positioned around the circle)
            float baseRadius = radius;
            float baseAngle1 = (float) Math.toRadians(angle - angleStep / 2);
            float baseAngle2 = (float) Math.toRadians(angle + angleStep / 2);

            float baseX1 = (float) (centerX + baseRadius * Math.sin(baseAngle1));
            float baseY1 = (float) (centerY - baseRadius * Math.cos(baseAngle1));

            float baseX2 = (float) (centerX + baseRadius * Math.sin(baseAngle2));
            float baseY2 = (float) (centerY - baseRadius * Math.cos(baseAngle2));

            // Create the path for the triangle
            Path path = new Path();
            path.moveTo(apexX, apexY); // Start from the apex
            path.lineTo(baseX1, baseY1); // First base point
            path.lineTo(baseX2, baseY2); // Second base point
            path.close();

            // Draw the triangle
            canvas.drawPath(path, trianglePaint);
        }
    }


    private float getMaxValueFromArray(float[] values) {
        float maxValue = Float.MIN_VALUE;
        for (float value : values) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }
}
