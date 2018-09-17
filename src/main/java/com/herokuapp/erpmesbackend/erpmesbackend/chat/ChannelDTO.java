package com.herokuapp.erpmesbackend.erpmesbackend.chat;

import com.herokuapp.erpmesbackend.erpmesbackend.employees.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ChannelDTO {

    private long id;
    private String name;
    private List<EmployeeDTO> participantDTOs;

    public ChannelDTO(Channel channel) {
        this.id = channel.getId();
        this.name = channel.getName();
        this.participantDTOs = new ArrayList<>();
        channel.getParticipants().forEach(participant -> this.participantDTOs.add(new EmployeeDTO(participant)));
    }

    public ChannelDTO(String name, List<EmployeeDTO> participantDTOs) {
        this.name = name;
        this.participantDTOs = participantDTOs;
    }

    public boolean checkIfDataEquals(ChannelDTO channelDTO) {
        return name.equals(channelDTO.getName()) &&
                compareParticipantDTOs(channelDTO.getParticipantDTOs());
    }

    private boolean compareParticipantDTOs(List<EmployeeDTO> participantDTOList) {
        if (participantDTOList.isEmpty())
            return true;
        for (EmployeeDTO participantDTO : participantDTOs) {
            if (participantDTOList.stream().noneMatch(t -> t.checkIfDataEquals(participantDTO)))
                return false;
        }
        return true;
    }
}