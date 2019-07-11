package service;

import com.example.progetto.controller.EUfund;
import org.springframework.stereotype.Component;
import java.util.Vector;


@Component
public class EUfundService {
    private static Vector<EUfund> Eufunds;
    private static Vector<Metadata> metadata;
    private static Vector<service.Metadata> Metadata;

    public Vector<EUfund> getEufunds() {
        return Eufunds;
    }

    public static void setEufunds(Vector<EUfund> Eufunds) {
        EUfundService.Eufunds = Eufunds;
    }

    public Vector<Metadata> getMetadata() {
        return Metadata;
    }

    public static void setMetadata(Vector<Metadata> metadata) {
        EUfundService.metadata = metadata;
    }
}
