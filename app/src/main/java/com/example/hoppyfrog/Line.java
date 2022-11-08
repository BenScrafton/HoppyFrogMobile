package com.example.hoppyfrog;

public class Line
{
    float gradient;
    float c;

    Vector2 bound1;
    Vector2 bound2;

    public float upperBoundx;
    public float upperBoundy;
    public float lowerBoundx;
    public float lowerBoundy;

    public Line(Vector2 p_bound1, Vector2 p_bound2)
    {
        bound1 = p_bound1;
        bound2 = p_bound2;

        CalculateLine();
    }

    void CalculateLine()
    {
        gradient = (bound1.y - bound2.y) / (bound1.x - bound2.x);
        c = bound1.y - (gradient * bound1.x);
        CalculateBounds();
    }

    void CalculateBounds()
    {
        if(bound1.x > bound2.x)
        {
            upperBoundx = bound1.x;
            lowerBoundx = bound2.x;
        }
        else
        {
            upperBoundx = bound2.x;
            lowerBoundx = bound1.x;
        }

        if(bound1.y > bound2.y)
        {
            upperBoundy = bound1.y;
            lowerBoundy = bound2.y;
        }
        else
        {
            upperBoundy = bound2.y;
            lowerBoundy = bound1.y;
        }
    }

    public Vector2 CalculateLineIntersect(Line otherLine)
    {
        float xIntersect;
        float yIntersect;

        if((gradient - otherLine.gradient) == 0)
        {
            return null;
        }

        xIntersect = (-1 * (c - otherLine.c)) / (gradient - otherLine.gradient);
        yIntersect = gradient * xIntersect + c;

        Vector2 intersect;

        if((xIntersect >= lowerBoundx) && (xIntersect <= upperBoundx) &&
                (xIntersect >= otherLine.lowerBoundx) && (xIntersect <= otherLine.upperBoundx) &&
                (yIntersect >= lowerBoundy) && (yIntersect <= upperBoundy) &&
                (yIntersect >= otherLine.lowerBoundy) && (yIntersect <= otherLine.lowerBoundy))
        {
            intersect = new Vector2(xIntersect, yIntersect);
        }
        else
        {
            return null;
        }

        return intersect;
    }

}
