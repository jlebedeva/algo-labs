/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package week3;

import java.util.List;

/**
 *
 * @author jane
 */
public class MinCutResult {
        List<Short> left;
        List<Short> right;
        short cuts;

        public MinCutResult(List<Short> left, List<Short> right, short cuts) {
            this.left = left;
            this.right = right;
            this.cuts = cuts;
        }
        
        @Override
        public String toString() {
            return "Number of cuts: " + cuts + "\nLeft: " + left + "\nRight: " + right;
        }
    }
