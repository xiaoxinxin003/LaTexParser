package com.daquexian.flexiblerichtextview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

import org.scilab.forge.jlatexmath.core.AjLatexMath;
import org.scilab.forge.jlatexmath.core.Insets;
import org.scilab.forge.jlatexmath.core.TeXConstants;
import org.scilab.forge.jlatexmath.core.TeXFormula;
import org.scilab.forge.jlatexmath.core.TeXIcon;

import java.util.List;

/**
 * Created by daquexian on 17-2-16.
 */

public class LaTeXtView extends TextView {
    private static final String TAG = "yxx-LaTexView";

    private int mFormulaTextSize = 15;
    private float mFormulaTextLeftMargin = 50;
    private float mFormulaTextTopMargin = 50;
    public LaTeXtView(Context context) {
        super(context);
    }

    public void setTextWithFormula(TextWithFormula textWithFormula) {
        List<TextWithFormula.Formula> formulas = textWithFormula.getFormulas();
        final SpannableStringBuilder builder = textWithFormula;

        for (final TextWithFormula.Formula formula : formulas) {
            TeXFormula teXFormula = TeXFormula.getPartialTeXFormula(formula.content);

           try {
                Bitmap bitmap = getBitmap(teXFormula);
                if (bitmap.getWidth() > FlexibleRichTextView.MAX_IMAGE_WIDTH) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, FlexibleRichTextView.MAX_IMAGE_WIDTH,
                            bitmap.getHeight() * FlexibleRichTextView.MAX_IMAGE_WIDTH / bitmap.getWidth(),
                            false);
                }

                builder.setSpan(new CenteredImageSpan(getContext(), bitmap),
                        formula.start, formula.end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
               Log.d(TAG, "setTextWithFormula: exception happened e:" + e.toString());
            }
        }

        setText(builder);
    }

    private Bitmap getBitmap(TeXFormula formula) {
        TeXIcon icon = formula.new TeXIconBuilder()
                .setStyle(TeXConstants.STYLE_DISPLAY)
                .setSize(mFormulaTextSize)
                .setWidth(TeXConstants.UNIT_SP, getPaint().getTextSize() / getPaint().density, TeXConstants.ALIGN_CENTER)
                .setIsMaxWidth(true)
                .setInterLineSpacing(TeXConstants.UNIT_SP,
                        AjLatexMath.getLeading(getPaint().getTextSize() / getPaint().density))
                .build();
        icon.setInsets(new Insets((int)mFormulaTextLeftMargin, (int)mFormulaTextTopMargin, 0, 0));

        Bitmap image = Bitmap.createBitmap(icon.getIconWidth(), icon.getIconHeight(),
                Bitmap.Config.ARGB_4444);

        Canvas g2 = new Canvas(image);
        g2.drawColor(Color.TRANSPARENT);
        icon.paintIcon(g2, 0, 0);
        return image;
    }

    public void setFormulaTextSize(int latexTextSize) {
        this.mFormulaTextSize = latexTextSize;
    }

    public void setFormulaLocation(float leftMargin, float topMargin) {
        this.mFormulaTextLeftMargin = leftMargin;
        this.mFormulaTextTopMargin = topMargin;
    }
}
