/* 
 * Copyright (C) 2016 RankSys http://ranksys.org
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.ranksys.context.rec;

import org.ranksys.context.ContextRecommendation;
import es.uam.eps.ir.ranksys.core.IdDouble;
import es.uam.eps.ir.ranksys.core.IdObject;
import es.uam.eps.ir.ranksys.rec.Recommender;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Context-aware recommender. Generates a list of recommended items for a user
 * in a given context.
 *
 * @author Saúl Vargas (Saul.Vargas@mendeley.com)
 * 
 * @param <U> user type
 * @param <C> context type
 * @param <I> item type
 */
public interface ContextRecommender<U, I, C> extends Recommender<IdObject<U, C>, I> {

    /**
     * Free recommendation. Generate recommendations without any restriction
     * on the items being recommended, but with a limit on the list size.
     *
     * @param u user to be issued a recommendation
     * @param maxLength maximum length of recommendation, set to 0 for no limit
     * @return a recommendation list
     */
    public default ContextRecommendation<U, I, C> getRecommendation(U u, C c, int maxLength) {
        List<IdDouble<I>> items = getRecommendation(new IdObject<>(u, c), maxLength).getItems();
        return new ContextRecommendation<>(u, c, items);
    }

    /**
     * Filter recommendation. Recommends only the items that pass the filter up
     * to a maximum list size.
     *
     * @param u user to be issued a recommendation
     * @param maxLength maximum length of recommendation, set to 0 for no limit
     * @param filter filter to decide which items might be recommended
     * @return a recommendation list
     */
    public default ContextRecommendation<U, I, C> getRecommendation(U u, C c, int maxLength, Predicate<I> filter) {
        List<IdDouble<I>> items = getRecommendation(new IdObject<>(u, c), maxLength, filter).getItems();
        return new ContextRecommendation<>(u, c, items);
    }

    /**
     * Candidates ranking. Create a list that may contain only the items
     * in the candidates set.
     *
     * @param u user to be issued a recommendation
     * @param candidates candidate items to be included in the recommendation
     * @return a recommendation list
     */
    public default ContextRecommendation<U, I, C> getRecommendation(U u, C c, Stream<I> candidates) {
        List<IdDouble<I>> items = getRecommendation(new IdObject<>(u, c), candidates).getItems();
        return new ContextRecommendation<>(u, c, items);
    }

}