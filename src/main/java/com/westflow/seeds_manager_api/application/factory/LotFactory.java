package com.westflow.seeds_manager_api.application.factory;

import com.westflow.seeds_manager_api.api.dto.request.LotRequest;
import com.westflow.seeds_manager_api.application.support.lot.LotContext;
import com.westflow.seeds_manager_api.application.usecase.lot.GenerateNextLotNumberUseCase;
import com.westflow.seeds_manager_api.domain.model.Lot;
import com.westflow.seeds_manager_api.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LotFactory {

    private final GenerateNextLotNumberUseCase generateNextLotNumberUseCase;

    public Lot create(LotRequest request, LotContext ctx, User user) {

        String lotNumber = generateNextLotNumberUseCase.execute();

        return Lot.newLot(
                lotNumber,
                request.getLotType(),
                request.getSeedType(),
                request.getCategory(),
                ctx.bagWeight(),
                ctx.bagType(),
                ctx.lab(),
                request.getQuantityTotal(),
                request.getProductionOrder(),
                request.getAnalysisBulletin(),
                request.getBulletinDate(),
                request.getHardSeeds(),
                request.getWildSeeds(),
                request.getOtherCultivatedSpecies(),
                request.getTolerated(),
                request.getProhibited(),
                request.getValidityDate(),
                request.getSeedScore(),
                request.getPurity(),
                user
        );
    }

    public Lot update(Lot existing,
                      LotRequest request,
                      LotContext ctx,
                      User user) {

        existing.update(
                request.getLotType(),
                request.getSeedType(),
                request.getCategory(),
                ctx.bagWeight(),
                ctx.bagType(),
                ctx.lab(),
                request.getQuantityTotal(),
                request.getProductionOrder(),
                request.getAnalysisBulletin(),
                request.getBulletinDate(),
                request.getHardSeeds(),
                request.getWildSeeds(),
                request.getOtherCultivatedSpecies(),
                request.getTolerated(),
                request.getProhibited(),
                request.getValidityDate(),
                request.getSeedScore(),
                request.getPurity(),
                user
        );

        return existing;
    }
}
