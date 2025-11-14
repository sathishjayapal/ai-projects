package me.sathish.sathishaidashboard.sathishragdashboard.config;

import me.sathish.sathishaidashboard.base.file.FileData;
import me.sathish.sathishaidashboard.sathishragdashboard.doc_d_b_data.DocDBData;
import me.sathish.sathishaidashboard.sathishragdashboard.doc_d_b_data.DocDBDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TestDataSathishragdashboard {

    @Autowired
    public DocDBDataRepository docDBDataRepository;

    public void clearAll() {
        docDBDataRepository.deleteAll();
    }

    public void docDBData() {
        final DocDBData docDBData = new DocDBData();
        docDBData.setId((long)1000);
        docDBData.setName("Zed diam voluptua.");
        final FileData fileName = new FileData();
        fileName.setUid("a9063e27-5d50-3f65-abf1-b02d926f19a4");
        fileName.setFileName("testFile.pdf");
        docDBData.setFileName(fileName);
        docDBDataRepository.save(docDBData);
        final DocDBData docDBData1 = new DocDBData();
        docDBData1.setId((long)1001);
        docDBData1.setName("At vero eos.");
        final FileData fileName1 = new FileData();
        fileName1.setUid("b8063e27-5d50-3f65-abf1-b02d926f19a4");
        fileName1.setFileName("testFile.pdf");
        docDBData1.setFileName(fileName1);
        docDBDataRepository.save(docDBData1);
    }

}
